package main.java.com.check.core.service;

import com.check.model.dto.LoginInfo;
import com.check.model.dto.SessionInfo;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(LoginInfo loginInfo, String uuid) throws Exception;

    SessionInfo register(LoginInfo loginInfo, String uuid) throws Exception;
}
