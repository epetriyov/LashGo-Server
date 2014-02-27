package main.java.com.check.service;

import com.check.model.dto.LoginInfo;
import com.check.model.dto.RegisterInfo;
import com.check.model.dto.SessionInfo;
import com.check.model.dto.SocialInfo;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(LoginInfo loginInfo, String uuid) throws Exception;

    SessionInfo register(RegisterInfo registerInfo, String uuid) throws Exception;

    SessionInfo registerBySocial(SocialInfo socialInfo, String uuid) throws Exception;
}
