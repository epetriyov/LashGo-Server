package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Sessions;
import com.lashgo.domain.Users;
import com.lashgo.model.dto.*;
import com.lashgo.repository.*;
import com.lashgo.model.ErrorCodes;
import com.lashgo.error.UnautharizedException;
import com.lashgo.error.ValidationException;
import com.lashgo.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
            int interfaceId = clientInterfaceDao.getIntefaceIdByCode(interfaceTypeCode);
            if (!userInterfaceDao.isUserInterfaceExists(users.getId(), interfaceId)) {
                userInterfaceDao.addUserInteface(users.getId(), interfaceId);
            }
            Sessions session = sessionDao.getSessionByUser(users.getId());
            if (session == null || System.currentTimeMillis() - session.getStartTime().getTime() > CheckConstants.SESSION_EXPIRE_PERIOD_MILLIS) {
                session = sessionDao.createSession(users.getId());
            }
            return new SessionInfo(session.getSessionId());
        } else {
            throw new ValidationException(ErrorCodes.USER_NOT_EXISTS);
        }
    }

    @Override
    @Transactional
    public void register(LoginInfo registerInfo) throws ValidationException {
        if (!userDao.isUserExists(registerInfo.getLogin())) {
            userDao.createUser(registerInfo);
        } else {
            throw new ValidationException(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void sendRecoverPassword(RecoverInfo recoverInfo) throws ValidationException {
        logger.debug("Recover password");
        if (userDao.isUserExists(recoverInfo.getEmail())) {
            String newPassword = generatePassword();
            userDao.updatePassword(recoverInfo.getEmail(), CheckUtils.md5(newPassword));
            SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
            mailMessage.setTo(recoverInfo.getEmail());
            mailMessage.setText(messageSource.getMessage("password.new", new Object[]{newPassword}, Locale.ENGLISH));
            mailSender.send(mailMessage);
        } else {
            throw new ValidationException(ErrorCodes.USER_NOT_EXISTS);
        }
    }

    private String generatePassword() {
        Random random = new Random();
        String pas = Integer.toString(random.nextInt((CheckConstants.MAX_PASSWORD_VALUE - CheckConstants.MIN_PASSWORD_VALUE) + 1) + CheckConstants.MIN_PASSWORD_VALUE);
        logger.debug(pas);
        return pas;
    }

    @Override
    public UserDto getProfile(String sessionId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        Users users = userDao.getUserById(sessions.getUserId());
        return new UserDto(users.getId(), users.getLogin(), users.getName(), users.getSurname(), users.getAbout(), users.getCity(), users.getBirthDate(), users.getAvatar(), users.getEmail());
    }

    @Override
    public List<PhotoDto> getPhotos(String sessionId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        Users users = userDao.getUserById(sessions.getUserId());
        return photoDao.getPhotosByUserId(users.getId());
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(String sessionId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        Users users = userDao.getUserById(sessions.getUserId());
        return subscriptionsDao.getSubscriptions(users.getId());
    }

    @Transactional
    @Override
    public void unsubscribe(String sessionId, int userId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        Users users = userDao.getUserById(sessions.getUserId());
        subscriptionsDao.removeSubscription(users.getId(), userId);
    }

    @Transactional
    @Override
    public void subscribe(String sessionId, int userId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        Users users = userDao.getUserById(sessions.getUserId());
        subscriptionsDao.addSubscription(users.getId(), userId);
    }

    private String buildSocialUserName(String socialName, String userId) {
        return new StringBuilder(socialName).append("_").append(userId).toString();
    }

    @Transactional
    @Override
    public SessionInfo socialSignIn(String interfaceTypeCode, SocialInfo socialInfo) {
        RegisterInfo registerInfo = null;
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
                registerInfo.setCity(facebookProfile.getLocation().getName());
                registerInfo.setName(facebookProfile.getName());
                registerInfo.setSurname(facebookProfile.getLastName());
                registerInfo.setGender(facebookProfile.getGender());
                break;
            case SocialNames.TWITTER:
                Twitter twitter = new TwitterTemplate(socialInfo.getAccessToken(), socialInfo.getAccessTokenSecret());
                TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();
                registerInfo = new RegisterInfo(buildSocialUserName(SocialNames.TWITTER, String.valueOf(twitterProfile.getId())), socialInfo.getAccessToken(), null);
                registerInfo.setName(twitterProfile.getName());
                registerInfo.setCity(twitterProfile.getLocation());
                registerInfo.setAvatar(twitterProfile.getProfileImageUrl());
                break;
            case SocialNames.VK:
                VKontakteTemplate vKontakteTemplate = new VKontakteTemplate(socialInfo.getAccessToken(), socialInfo.getAccessTokenSecret());
                VKontakteProfile vKontakteProfile = vKontakteTemplate.usersOperations().getUser();
                registerInfo = new RegisterInfo(buildSocialUserName(SocialNames.VK, vKontakteProfile.getUid()), socialInfo.getAccessToken(), null);
                registerInfo.setAvatar(vKontakteProfile.getPhotoMedium());
                registerInfo.setName(vKontakteProfile.getFirstName());
                registerInfo.setSurname(vKontakteProfile.getLastName());
                Calendar calender = Calendar.getInstance();
                calender.set(vKontakteProfile.getBirthDate().getYear(), vKontakteProfile.getBirthDate().getMonth(), vKontakteProfile.getBirthDate().getDay());
                registerInfo.setBirthDate(calender.getTime());
                registerInfo.setGender(vKontakteProfile.getGender());
                break;
            default:
                throw new ValidationException(ErrorCodes.UNSUPPORTED_SOCIAL);
        }
        SessionInfo sessionInfo = innerLogin(interfaceTypeCode, registerInfo);
        if (sessionInfo == null) {
            if (SocialNames.VK.equals(socialInfo.getSocialName()) || SocialNames.TWITTER.equals(socialInfo.getSocialName())) {
                userDao.createTempUser(registerInfo);
                throw new ValidationException(ErrorCodes.EMAIL_NEEDED);
            } else {
                userDao.createUser(registerInfo);
                return innerLogin(interfaceTypeCode, registerInfo);
            }
        }
        return sessionInfo;
    }

    @Override
    public SessionInfo socialSignUp(String interfaceTypeCode, ExtendedSocialInfo socialInfo) {
        LoginInfo loginInfo = null;
        switch (socialInfo.getSocialName()) {
            case SocialNames.FACEBOOK:
                Facebook facebook = new FacebookTemplate(socialInfo.getAccessToken());
                FacebookProfile facebookProfile = facebook.userOperations().getUserProfile();
                loginInfo = new LoginInfo(buildSocialUserName(SocialNames.FACEBOOK, facebookProfile.getId()), socialInfo.getAccessToken());
                break;
            case SocialNames.TWITTER:
                Twitter twitter = new TwitterTemplate(socialInfo.getAccessToken(), socialInfo.getAccessTokenSecret());
                TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();
                loginInfo = new LoginInfo(buildSocialUserName(SocialNames.TWITTER, String.valueOf(twitterProfile.getId())), socialInfo.getAccessToken());
                break;
            case SocialNames.VK:
                VKontakteTemplate vKontakteTemplate = new VKontakteTemplate(socialInfo.getAccessToken(), socialInfo.getAccessTokenSecret());
                VKontakteProfile vKontakteProfile = vKontakteTemplate.usersOperations().getUser();
                loginInfo = new LoginInfo(buildSocialUserName(SocialNames.VK, vKontakteProfile.getUid()), socialInfo.getAccessTokenSecret());
                break;
            default:
                throw new ValidationException(ErrorCodes.UNSUPPORTED_SOCIAL);
        }
        Users tempUser = userDao.findTempUser(loginInfo.getLogin());
        if (tempUser == null) {
            throw new ValidationException(ErrorCodes.TEMP_USER_NOT_EXISTS);
        }
        tempUser.setEmail(socialInfo.getEmail());
        tempUser.setPassword(loginInfo.getPasswordHash());
        userDao.createSocialUser(tempUser);
        return innerLogin(interfaceTypeCode, loginInfo);
    }

    @Override
    public MainScreenInfoDto getMainScreenInfo(String sessionId, UserLastViews userLastViews) {
        UserDto userDto = getProfile(sessionId);
        MainScreenInfoDto mainScreenInfoDto = new MainScreenInfoDto();
        mainScreenInfoDto.setUserAvatar(userDto.getAvatar());
        mainScreenInfoDto.setUserName(userDto.getLogin());
        mainScreenInfoDto.setNewsCount(newsDao.getNewerNews(userLastViews.getNewsLastView()));
        mainScreenInfoDto.setSubscribesCount(subscriptionsDao.getNewerSubscriptions(userDto.getId(), userLastViews.getSubscribesLastView()));
        mainScreenInfoDto.setTasksCount(checkDao.getActiveChecksCount());
        return mainScreenInfoDto;
    }


}
