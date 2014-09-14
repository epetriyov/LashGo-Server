package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Sessions;
import com.lashgo.domain.Users;
import com.lashgo.error.PhotoReadException;
import com.lashgo.error.PhotoWriteException;
import com.lashgo.error.UnautharizedException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.*;
import com.lashgo.repository.*;
import com.lashgo.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.social.ApiException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.vkontakte.api.VKontakteProfile;
import org.springframework.social.vkontakte.api.impl.VKontakteTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Eugene on 13.02.14.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private SocialDao socialDao;

    @Autowired
    private UserSocialDao userSocialDao;

    @Autowired
    private UserInterfaceDao userInterfaceDao;

    @Autowired
    private ClientInterfaceDao clientInterfaceDao;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private SubscriptionsDao subscriptionsDao;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    private final Logger logger = LoggerFactory.getLogger("FILE");

    @Transactional
    @Override
    public SessionInfo login(String interfaceTypeCode, LoginInfo loginInfo) throws ValidationException, UnautharizedException {
        SessionInfo sessionInfo = innerLogin(interfaceTypeCode, loginInfo);
        if (sessionInfo == null) {
            throw new ValidationException(ErrorCodes.USER_NOT_EXISTS);
        }
        return sessionInfo;
    }

    private SessionInfo innerLogin(String interfaceTypeCode, LoginInfo loginInfo) {
        Users users = userDao.findUser(loginInfo);
        if (users != null) {
            return getSessionByUser(users.getId(), interfaceTypeCode);
        } else {
            return null;
        }
    }

    private SessionInfo getSessionByUser(int userId, String interfaceTypeCode) {
        int interfaceId = clientInterfaceDao.getIntefaceIdByCode(interfaceTypeCode);
        if (!userInterfaceDao.isUserInterfaceExists(userId, interfaceId)) {
            userInterfaceDao.addUserInteface(userId, interfaceId);
        }
        Sessions session = sessionDao.getSessionByUser(userId);
        if (session == null || System.currentTimeMillis() - session.getStartTime().getTime() > CheckConstants.SESSION_EXPIRE_PERIOD_MILLIS) {
            session = sessionDao.createSession(userId);
        }
        return new SessionInfo(session.getSessionId(), userId);
    }

    @Override
    @Transactional
    public RegisterResponse register(String interfaceTypeCode, LoginInfo registerInfo) throws ValidationException {
        if (!userDao.isUserExists(registerInfo.getLogin())) {
            userDao.createUser(registerInfo);
            return buildRegisterResponse(interfaceTypeCode, registerInfo);
        } else {
            throw new ValidationException(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void sendRecoverPassword(String email) throws ValidationException {
        if (email != null && email.length() > 0) {
            if (userDao.isUserExists(email)) {
                String newPassword = generatePassword();
                userDao.updatePassword(email, CheckUtils.md5(newPassword));
                SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
                mailMessage.setTo(email);
                mailMessage.setText(messageSource.getMessage("password.new", new Object[]{email, newPassword}, Locale.ENGLISH));
                mailSender.send(mailMessage);
            } else {
                throw new ValidationException(ErrorCodes.USER_NOT_EXISTS);
            }
        } else {
            throw new ValidationException(ErrorCodes.EMPTY_EMAIL);
        }
    }

    private String generatePassword() {
        Random random = new Random();
        String pas = Integer.toString(random.nextInt((CheckConstants.MAX_PASSWORD_VALUE - CheckConstants.MIN_PASSWORD_VALUE) + 1) + CheckConstants.MIN_PASSWORD_VALUE);
        logger.debug(pas);
        return pas;
    }

    @Override
    public UserDto getProfile(int userId) {
        return userDao.getUserProfile(userId);
    }

    @Override
    public List<PhotoDto> getPhotos(String sessionId) {
        Users users = getUserBySession(sessionId);
        return photoDao.getPhotosByUserId(users.getId());
    }

    public Users getUserBySession(String sessionId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        return userDao.getUserById(sessions.getUserId());
    }

    @Override
    public UserDto getProfile(String sessionId) {
        return userDao.getUserProfile(getUserBySession(sessionId).getId());
    }

    @Override
    public List<PhotoDto> getPhotos(int userId) {
        return photoDao.getPhotosByUserId(userId);
    }

    @Override
    @Transactional
    public void saveAvatar(String sessionId, MultipartFile avatar) {
        Users users = getUserBySession(sessionId);
        if (!avatar.isEmpty()) {
            BufferedImage src = null;
            try {
                src = ImageIO.read(new ByteArrayInputStream(avatar.getBytes()));
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new PhotoReadException();
            }
            StringBuilder photoDestinationBuilder = new StringBuilder(CheckConstants.PHOTOS_FOLDER);
            String photoName = buildNewPhotoName(users.getId());
            String photoPath = photoDestinationBuilder.append(photoName).toString();
            File destination = new File(photoPath);
            try {
                ImageIO.write(src, "jpg", destination);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new PhotoWriteException();
            }
            userDao.updateAvatar(photoName, users.getId());
        }
    }

    @Override
    @Transactional
    public void updateProfile(String sessionId, UserDto userDto) {
        Users user = getUserBySession(sessionId);
        if (user.getId() != userDto.getId()) {
            throw new ValidationException(ErrorCodes.USERS_DOESNT_MATCHES);
        }
        if (!StringUtils.isEmpty(userDto.getLogin()) && userDao.isUserExists(user.getId(), userDto.getLogin())) {
            throw new ValidationException(ErrorCodes.USER_WITH_LOGIN_ALREADY_EXISTS);
        }
        if (!StringUtils.isEmpty(userDto.getEmail()) && userDao.isUserExists(user.getId(), userDto.getEmail())) {
            throw new ValidationException(ErrorCodes.USER_WITH_EMAIL_ALREADY_EXISTS);
        }
        userDao.updateProfile(user.getId(), userDto);
    }

    private String buildNewPhotoName(int userId) {
        StringBuilder photoNameBuilder = new StringBuilder("avatar_");
        photoNameBuilder.append("_user_");
        photoNameBuilder.append(userId);
        photoNameBuilder.append(".jpg");
        return photoNameBuilder.toString();
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(String sessionId) {
        Users users = getUserBySession(sessionId);
        return subscriptionsDao.getSubscriptions(users.getId());
    }

    @Transactional
    @Override
    public void unsubscribe(String sessionId, int userId) {
        Users users = getUserBySession(sessionId);
        subscriptionsDao.removeSubscription(users.getId(), userId);
    }

    @Transactional
    @Override
    public void subscribe(String sessionId, int userId) {
        Users users = getUserBySession(sessionId);
        subscriptionsDao.addSubscription(users.getId(), userId);
    }

    private String buildSocialUserName(String socialName, String userId) {
        return new StringBuilder(socialName).append("_").append(userId).toString();
    }

    @Transactional
    @Override
    public RegisterResponse socialSignIn(String interfaceTypeCode, SocialInfo socialInfo) {
        RegisterInfo registerInfo = null;
        try {
            switch (socialInfo.getSocialName()) {
                case SocialNames.FACEBOOK:
                    Facebook facebook = new FacebookTemplate(socialInfo.getAccessToken());
                    FacebookProfile facebookProfile = facebook.userOperations().getUserProfile();
                    registerInfo = new RegisterInfo(buildSocialUserName(SocialNames.FACEBOOK, facebookProfile.getId()), socialInfo.getAccessToken(), facebookProfile.getEmail());
                    registerInfo.setAbout(facebookProfile.getAbout());
                    try {
                        registerInfo.setBirthDate(new SimpleDateFormat(CheckConstants.FACEBOOK_DATE_FORMAT).parse(facebookProfile.getBirthday()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (facebookProfile.getLocation() != null) {
                        registerInfo.setCity(facebookProfile.getLocation().getName());
                    }
                    registerInfo.setFio(facebookProfile.getName() + " " + facebookProfile.getLastName());
                    break;
                case SocialNames.TWITTER:
                    Twitter twitter = new TwitterTemplate(CheckConstants.TWITTER_CONSUMER_KEY, CheckConstants.TWITTER_CONSUMER_SECRET_KEY, socialInfo.getAccessToken(), socialInfo.getAccessTokenSecret());
                    TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();
                    registerInfo = new RegisterInfo(buildSocialUserName(SocialNames.TWITTER, String.valueOf(twitterProfile.getId())), socialInfo.getAccessToken(), null);
                    registerInfo.setFio(twitterProfile.getName());
                    registerInfo.setCity(twitterProfile.getLocation());
                    registerInfo.setAvatar(twitterProfile.getProfileImageUrl());
                    break;
                case SocialNames.VK:
                    VKontakteTemplate vKontakteTemplate = new VKontakteTemplate(socialInfo.getAccessToken(), socialInfo.getAccessTokenSecret());
                    VKontakteProfile vKontakteProfile = vKontakteTemplate.usersOperations().getUser();
                    registerInfo = new RegisterInfo(buildSocialUserName(SocialNames.VK, vKontakteProfile.getUid()), socialInfo.getAccessToken(), null);
                    registerInfo.setAvatar(vKontakteProfile.getPhotoMedium());
                    registerInfo.setFio(vKontakteProfile.getFirstName() + " " + vKontakteProfile.getLastName());
                    Calendar calender = Calendar.getInstance();
                    calender.set(vKontakteProfile.getBirthDate().getYear(), vKontakteProfile.getBirthDate().getMonth(), vKontakteProfile.getBirthDate().getDay());
                    registerInfo.setBirthDate(calender.getTime());
                    break;
                default:
                    throw new ValidationException(ErrorCodes.UNSUPPORTED_SOCIAL);
            }
        } catch (ApiException e) {
            throw new ValidationException(ErrorCodes.SOCIAL_WRONG_DATA);
        }

        if (socialDao.isSocialExists(registerInfo.getLogin())) {
            /**
             * social user exists
             */
            int userId = userSocialDao.getUserBySocial(registerInfo.getLogin());
            SessionInfo sessionInfo = getSessionByUser(userId, interfaceTypeCode);
            return new RegisterResponse(sessionInfo.getSessionId());
        } else {
            /**
             * create social user
             */
            socialDao.createSocial(registerInfo.getLogin(), registerInfo.getPasswordHash());
            userDao.createUser(registerInfo);
            Users users = userDao.findUser(registerInfo);
            System.out.println("Login: " + registerInfo.getLogin());
            System.out.println("Password: " + registerInfo.getPasswordHash());
            System.out.println("UserId: " + users.getId());
            userSocialDao.createUserSocial(users.getId(), registerInfo.getLogin());
            return buildRegisterResponse(interfaceTypeCode, registerInfo);
        }
    }

    private RegisterResponse buildRegisterResponse(String interfaceTypeCode, LoginInfo loginInfo) {
        SessionInfo sessionInfo = innerLogin(interfaceTypeCode, loginInfo);
        if (sessionInfo != null) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setUserDto(userDao.getUserProfile(userDao.findUser(loginInfo).getId()));
            registerResponse.setSessionId(sessionInfo.getSessionId());
            return registerResponse;
        } else {
            throw new ValidationException(ErrorCodes.USER_NOT_EXISTS);
        }
    }

    @Override
    public MainScreenInfoDto getMainScreenInfo(String sessionId, UserLastViews userLastViews) {
        Users userDto = getUserBySession(sessionId);
        MainScreenInfoDto mainScreenInfoDto = new MainScreenInfoDto();
        mainScreenInfoDto.setUserAvatar(userDto.getAvatar());
        mainScreenInfoDto.setUserName(userDto.getFio() != null ? userDto.getFio(): userDto.getLogin());
        mainScreenInfoDto.setNewsCount(newsDao.getNewerNews(userLastViews.getNewsLastView()));
        mainScreenInfoDto.setSubscribesCount(subscriptionsDao.getNewerSubscriptions(userDto.getId(), userLastViews.getSubscribesLastView()));
        mainScreenInfoDto.setTasksCount(checkDao.getActiveChecksCount());
        return mainScreenInfoDto;
    }
}
