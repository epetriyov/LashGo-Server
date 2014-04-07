package main.java.com.check.service;

import com.check.model.dto.*;
import main.java.com.check.CheckConstants;
import main.java.com.check.domain.Sessions;
import main.java.com.check.domain.Users;
import main.java.com.check.repository.SessionDao;
import main.java.com.check.repository.UserDao;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.UnautharizedException;
import main.java.com.check.rest.error.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Eugene on 13.02.14.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    public SessionInfo login(LoginInfo loginInfo, String uuid) throws ValidationException, UnautharizedException {
        Users users = userDao.findUser(loginInfo);
        if (users != null) {
            Sessions session = sessionDao.getSession(uuid, users.getId());
            if (session == null) {
                session = sessionDao.saveSession(uuid, users.getId());
            } else if (System.currentTimeMillis() - session.getStartTime().getTime() > CheckConstants.SESSION_EXPIRE_PERIOD_MILLIS) {
                throw new UnautharizedException(ErrorCodes.SESSION_IS_EMPTY);
            }
            return new SessionInfo(session.getSessionId());
        } else {
            throw new ValidationException(ErrorCodes.USER_NOT_FOUND);
        }
    }

    @Override
    public void register(RegisterInfo registerInfo) throws ValidationException {
        if (!userDao.isUserExists(registerInfo.getLogin())) {
            userDao.createUser(registerInfo);
        } else {
            throw new ValidationException(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }

    @Override
    public void registerBySocial(SocialInfo socialInfo) throws ValidationException {
        if (!userDao.isUserExists(socialInfo.getLogin())) {
            userDao.createUser(socialInfo);
        } else {
            throw new ValidationException(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }
}
