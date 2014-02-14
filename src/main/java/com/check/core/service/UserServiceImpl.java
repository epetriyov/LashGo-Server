package main.java.com.check.core.service;

import main.java.com.check.core.domain.Users;
import main.java.com.check.core.repository.UserDao;
import main.java.com.check.rest.dto.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Eugene on 13.02.14.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Users login(LoginInfo loginInfo){
        return userDao.findUser(loginInfo);
    }

    @Override
    @Transactional
    public void register(LoginInfo loginInfo) {
        userDao.createUser(loginInfo);
    }
}
