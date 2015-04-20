package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Sessions;
import com.lashgo.domain.Users;
import com.lashgo.error.UnautharizedException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.CheckType;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.*;
import com.lashgo.repository.*;
import com.lashgo.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Eugene on 13.02.14.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private PhotoService photoService;

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
        if (session == null) {
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
    public void sendRecoverPassword(RecoverInfo email) throws ValidationException {
        if (email != null && email.getEmail() != null && email.getEmail().length() > 0) {
            if (userDao.isUserExists(email.getEmail())) {
                String newPassword = generatePassword();
                userDao.updatePassword(email.getEmail(), CheckUtils.md5(newPassword));
                SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
                mailMessage.setTo(email.getEmail());
                mailMessage.setText(String.format("New password for user %s: %s", email, newPassword));
                mailSender.send(mailMessage);
            } else {
                throw new ValidationException(ErrorCodes.USER_NOT_EXISTS);
            }
        } else {
            logger.error("Empty email was sent");
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
    public UserDto getProfile(String sessionId, int userId) {
        Users users = null;
        if (sessionId != null) {
            users = getUserBySession(sessionId);
        }
        UserDto userDto = userDao.getUserProfile(users != null ? users.getId() : -1, userId);
        if (userDto == null) {
            throw new ValidationException(ErrorCodes.USER_NOT_EXISTS);
        }
        return userDto;
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
        String photoName = buildNewPhotoName(users.getId());
        photoService.savePhoto(photoName, avatar);
        userDao.updateAvatar(photoName, users.getId());
        File oldAvatar = new File(CheckConstants.PHOTOS_FOLDER + users.getAvatar());
        if (oldAvatar.exists()) {
            oldAvatar.delete();
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

    @Override
    public List<SubscriptionDto> findUsers(String sessionId, String searchText) {
        Users users = null;
        if (sessionId != null) {
            users = getUserBySession(sessionId);
        }
        if (!StringUtils.isEmpty(searchText)) {
            return userDao.findUsers(searchText, users != null ? users.getId() : -1);
        }
        return Collections.EMPTY_LIST;
    }

    private String buildNewPhotoName(int userId) {
        StringBuilder photoNameBuilder = new StringBuilder("avatar");
        photoNameBuilder.append("_user_");
        photoNameBuilder.append(userId);
        photoNameBuilder.append("_");
        photoNameBuilder.append(System.currentTimeMillis());
        photoNameBuilder.append(".jpg");
        return photoNameBuilder.toString();
    }

    @Override
    public List<SubscriptionDto> getMySubscriptions(String sessionId) {
        Users users = getUserBySession(sessionId);
        return subscriptionsDao.getSubscriptions(users.getId(), users.getId());
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(String sessionId, int userId) {
        Users users = null;
        if (sessionId != null) {
            users = getUserBySession(sessionId);
        }
        return subscriptionsDao.getSubscriptions(userId, users != null ? users.getId() : -1);
    }

    @Override
    public List<SubscriptionDto> getMySubscribers(String sessionId) {
        Users users = getUserBySession(sessionId);
        return subscriptionsDao.getSubscribers(users.getId(), users.getId());
    }

    @Override
    public List<SubscriptionDto> getSubscribers(String sessionId, int userId) {
        Users users = null;
        if (sessionId != null) {
            users = getUserBySession(sessionId);
        }
        return subscriptionsDao.getSubscribers(userId, users != null ? users.getId() : -1);
    }

    @Transactional
    @Override
    public void unsubscribe(String sessionId, int userId) {
        Users users = getUserBySession(sessionId);
        if (subscriptionsDao.isSubscriptionExists(users.getId(), userId)) {
            subscriptionsDao.removeSubscription(users.getId(), userId);
        } else {
            throw new ValidationException(ErrorCodes.SUBSCRIPTION_NOT_EXISTS);
        }
    }

    @Transactional
    @Override
    public void subscribe(String sessionId, int userId) {
        Users users = getUserBySession(sessionId);
        if (subscriptionsDao.isSubscriptionExists(users.getId(), userId)) {
            throw new ValidationException(ErrorCodes.SUBSCRIPTION_ALREADY_EXISTS);
        } else {
            if (userId == users.getId()) {
                throw new ValidationException(ErrorCodes.CANT_SUBSCRIBE_TO_YOURSELF);
            }
            subscriptionsDao.addSubscription(users.getId(), userId);
            eventDao.addSibscribeEvent(users.getId(), userId);
        }
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
                    if (facebookProfile.getBirthday() != null) {
                        try {
                            registerInfo.setBirthDate(new SimpleDateFormat(CheckConstants.FACEBOOK_DATE_FORMAT).parse(facebookProfile.getBirthday()));
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
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
                    if (vKontakteProfile.getBirthDate() != null) {
                        Calendar calender = Calendar.getInstance();
                        calender.set(vKontakteProfile.getBirthDate().getYear(), vKontakteProfile.getBirthDate().getMonth(), vKontakteProfile.getBirthDate().getDay());
                        registerInfo.setBirthDate(calender.getTime());
                    }
                    break;
                default:
                    throw new ValidationException(ErrorCodes.UNSUPPORTED_SOCIAL);
            }
        } catch (ApiException e) {
            logger.error(e.getMessage());
            throw new ValidationException(ErrorCodes.SOCIAL_WRONG_DATA);
        }

        if (socialDao.isSocialExists(registerInfo.getLogin())) {
            /**
             * social user exists
             */
            int userId = userSocialDao.getUserBySocial(registerInfo.getLogin());
            SessionInfo sessionInfo = getSessionByUser(userId, interfaceTypeCode);
            return new RegisterResponse(sessionInfo);
        } else {
            /**
             * create social user
             */
            socialDao.createSocial(registerInfo.getLogin(), registerInfo.getPasswordHash());
            userDao.createUser(registerInfo);
            Users users = userDao.findUser(registerInfo);
            userSocialDao.createUserSocial(users.getId(), registerInfo.getLogin());
            return buildRegisterResponse(interfaceTypeCode, registerInfo);
        }
    }

    private RegisterResponse buildRegisterResponse(String interfaceTypeCode, LoginInfo loginInfo) {
        SessionInfo sessionInfo = innerLogin(interfaceTypeCode, loginInfo);
        if (sessionInfo != null) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setUserDto(userDao.getUserProfile(userDao.findUser(loginInfo).getId()));
            registerResponse.setSessionIInfo(sessionInfo);
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
        mainScreenInfoDto.setUserName(userDto.getFio() != null ? userDto.getFio() : userDto.getLogin());
        mainScreenInfoDto.setNewsCount(newsDao.getNewerNews(userLastViews.getNewsLastView()));
        mainScreenInfoDto.setSubscribesCount(eventDao.getEventsCountByUser(userDto.getId(), userLastViews.getSubscribesLastView()));
        mainScreenInfoDto.setTasksCount(checkDao.getActiveChecksCount(CheckType.SELFIE));
        mainScreenInfoDto.setActionCount(checkDao.getActiveChecksCount(CheckType.ACTION));
        return mainScreenInfoDto;
    }

    public List<SubscriptionDto> getUsersByVotes(String sessionId, long photoId) {
        int userId = -1;
        if (sessionId != null) {
            Users userDto = getUserBySession(sessionId);
            userId = userDto.getId();
        }
        return userDao.getUsersByVotes(userId, photoId);
    }
}
