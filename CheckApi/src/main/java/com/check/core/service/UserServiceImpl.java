package main.java.com.check.core.service;

import main.java.com.check.core.domain.Users;
import main.java.com.check.core.repository.SessionDao;
import main.java.com.check.core.repository.UserDao;
import com.check.model.dto.LoginInfo;
import com.check.model.dto.SessionInfo;
import main.java.com.check.rest.error.ErrorCodes;
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
    public SessionInfo login(LoginInfo loginInfo, String uuid) throws Exception {
        Users users = userDao.findUser(loginInfo);
        if (users != null) {
            String session = sessionDao.getSession(uuid, users.getId());
            if (session == null) {
                session = sessionDao.saveSession(uuid, users.getId());
            }
            return new SessionInfo(session);
        } else {
            throw new Exception(ErrorCodes.USER_NOT_FOUND);
        }
    }

    @Override
    public SessionInfo register(LoginInfo loginInfo, String uuid) throws Exception {
        if (userDao.isUserExists(loginInfo.getLogin())) {
            userDao.createUser(loginInfo);
            return login(loginInfo, uuid);
        } else {
            throw new Exception(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }
}
