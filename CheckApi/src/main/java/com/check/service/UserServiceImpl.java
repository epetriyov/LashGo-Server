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
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    @Transactional
    public SessionInfo login(LoginInfo loginInfo) throws ValidationException, UnautharizedException {
        Users users = userDao.findUser(loginInfo);
        if (users != null) {
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
}
