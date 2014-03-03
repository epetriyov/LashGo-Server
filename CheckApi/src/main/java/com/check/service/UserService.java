package main.java.com.check.service;

import com.check.model.dto.*;
import main.java.com.check.rest.error.LoginException;
import main.java.com.check.rest.error.RegisterException;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(LoginInfo loginInfo, String uuid) throws LoginException;

    SessionInfo register(RegisterInfo registerInfo, String uuid) throws RegisterException;

    SessionInfo registerBySocial(SocialInfo socialInfo, String uuid) throws RegisterException;

}
