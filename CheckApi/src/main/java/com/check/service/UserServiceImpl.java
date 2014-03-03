package main.java.com.check.service;

import com.check.model.dto.*;
import main.java.com.check.domain.Users;
import main.java.com.check.repository.SessionDao;
import main.java.com.check.repository.UserDao;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.LoginException;
import main.java.com.check.rest.error.RegisterException;
import main.java.com.check.utils.CheckUtils;
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
    public SessionInfo login(LoginInfo loginInfo, String uuid) throws LoginException {
        Users users = userDao.findUser(loginInfo);
        if (users != null) {
            String session = sessionDao.getSession(uuid, users.getId());
            if (session == null) {
                session = sessionDao.saveSession(uuid, users.getId());
            }
            return new SessionInfo(session);
        } else {
            throw new LoginException(ErrorCodes.USER_NOT_FOUND);
        }
    }

    @Override
    public SessionInfo register(RegisterInfo registerInfo, String uuid) throws RegisterException {
        if (!userDao.isUserExists(registerInfo.getLogin())) {
            userDao.createUser(registerInfo);
            try {
                return login(registerInfo, uuid);
            } catch (LoginException e) {
                e.printStackTrace();
                /**
                 * will not occur
                 */
                return null;
            }
        } else {
            throw new RegisterException(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }

    @Override
    public SessionInfo registerBySocial(SocialInfo socialInfo, String uuid) throws RegisterException {
        if (!userDao.isUserExists(socialInfo.getLogin())) {
            LoginInfo loginInfo = userDao.createUser(socialInfo);
            try {
                return login(loginInfo, uuid);
            } catch (LoginException e) {
                e.printStackTrace();
                /**
                 * will not occur
                 */
                return null;
            }
        } else {
            throw new RegisterException(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }
}
