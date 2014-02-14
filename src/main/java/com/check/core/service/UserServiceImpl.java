package main.java.com.check.core.service;

import main.java.com.check.core.domain.Users;
import main.java.com.check.core.repository.SessionDao;
import main.java.com.check.core.repository.UserDao;
import main.java.com.check.rest.dto.LoginInfo;
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
    public String login(LoginInfo loginInfo, String uuid) throws Exception {
        Users users = userDao.findUser(loginInfo);
        if (users != null) {
            return sessionDao.saveSession(uuid, users.getId());
        } else {
            throw new Exception("user does not exists");
        }
    }

    @Override
    public void register(LoginInfo loginInfo) {
        userDao.createUser(loginInfo);
    }
}
