package main.java.com.check.service;

import com.check.model.dto.*;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(LoginInfo loginInfo, String uuid) throws Exception;

    SessionInfo register(RegisterInfo registerInfo, String uuid) throws Exception;

    SessionInfo registerBySocial(SocialInfo socialInfo, String uuid) throws Exception;

}
