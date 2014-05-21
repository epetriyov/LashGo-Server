package main.java.com.lashgo.service;

import com.lashgo.model.dto.*;
import main.java.com.lashgo.CheckConstants;
import main.java.com.lashgo.domain.Sessions;
import main.java.com.lashgo.domain.Users;
import main.java.com.lashgo.repository.*;
import main.java.com.lashgo.error.ErrorCodes;
import main.java.com.lashgo.error.UnautharizedException;
import main.java.com.lashgo.error.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
    private CheckDao checkDao;

    @Transactional
    @Override
    public SessionInfo login(String interfaceTypeCode, LoginInfo loginInfo) throws ValidationException, UnautharizedException {
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
            throw new ValidationException(ErrorCodes.USER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void register(RegisterInfo registerInfo) throws ValidationException {
        if (!userDao.isUserExists(registerInfo.getLogin())) {
            userDao.createUser(registerInfo);
        } else {
            throw new ValidationException(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void sendRecoverPassword(RecoverInfo recoverInfo) throws ValidationException {
        //TODO implement
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


}
