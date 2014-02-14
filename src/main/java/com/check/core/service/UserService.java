package main.java.com.check.core.service;

import main.java.com.check.core.domain.Users;
import main.java.com.check.rest.dto.LoginInfo;
import org.springframework.stereotype.Service;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    String login(LoginInfo loginInfo, String uuid) throws Exception;

    void register(LoginInfo loginInfo);
}
