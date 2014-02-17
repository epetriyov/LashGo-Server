package main.java.com.check.rest.controller;

import main.java.com.check.core.service.UserService;
import com.check.model.dto.LoginInfo;
import com.check.model.dto.SessionInfo;
import main.java.com.check.rest.error.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public
    @ResponseBody
    SessionInfo login(@RequestHeader HttpHeaders requestHeaders, @RequestBody LoginInfo loginInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        if (!CollectionUtils.isEmpty(uuidHeaders)) {
            SessionInfo sessionInfo = userService.login(loginInfo, uuidHeaders.get(0));
            if (sessionInfo == null) {
                throw new Exception(ErrorCodes.USER_NOT_FOUND);
            } else {
                return sessionInfo;
            }
        } else {
            throw new Exception(ErrorCodes.UUID_IS_EMPTY);
        }
    }

    @RequestMapping("/register")
    public
    @ResponseBody
    SessionInfo register(@RequestHeader HttpHeaders requestHeaders, @RequestBody LoginInfo loginInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        if (!CollectionUtils.isEmpty(uuidHeaders)) {
            return userService.register(loginInfo, uuidHeaders.get(0));
        } else {
            throw new Exception(ErrorCodes.UUID_IS_EMPTY);
        }
    }
}
