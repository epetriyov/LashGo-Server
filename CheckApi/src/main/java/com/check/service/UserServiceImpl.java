package main.java.com.check.service;

import com.check.model.dto.*;
import main.java.com.check.domain.Users;
import main.java.com.check.repository.SessionDao;
import main.java.com.check.repository.UserDao;
import main.java.com.check.rest.error.ErrorCodes;
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
    public SessionInfo register(RegisterInfo registerInfo, String uuid) throws Exception {
        if (!userDao.isUserExists(registerInfo.getLogin())) {
            userDao.createUser(registerInfo);
            return login(registerInfo, uuid);
        } else {
            throw new Exception(ErrorCodes.USER_ALREADY_EXISTS);
        }
    }

    @Override
    public SessionInfo registerBySocial(SocialInfo socialInfo, String uuid) throws Exception {
        Users user = userDao.findSocialUser(socialInfo.getUserName(), socialInfo.getSocialType());
        if (user == null) {
            if (!CheckUtils.isEmpty(socialInfo.getEmail())) {
                LoginInfo loginInfo = userDao.createUser(socialInfo);
                return login(loginInfo, uuid);
            }
            return null;
        } else {
            return login(new LoginInfo(user.getLogin(), user.getPasswordHash()), uuid);
        }
    }
}
