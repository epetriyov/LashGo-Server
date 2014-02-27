package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.RegisterInfo;
import com.check.model.dto.SocialInfo;
import main.java.com.check.service.UserService;
import com.check.model.dto.LoginInfo;
import com.check.model.dto.SessionInfo;
import main.java.com.check.rest.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
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
        UserValidator.validateLogin(loginInfo);
        return userService.login(loginInfo, uuidHeaders.get(0));
    }

    @RequestMapping("/register")
    public
    @ResponseBody
    SessionInfo register(@RequestHeader HttpHeaders requestHeaders, @RequestBody RegisterInfo registerInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        UserValidator.validateRegsiter(registerInfo);
        return userService.register(registerInfo, uuidHeaders.get(0));
    }

    @RequestMapping("/sign-in-with-social")
    public
    @ResponseBody
    SessionInfo signInWithSocial(@RequestHeader HttpHeaders requestHeaders, @RequestBody SocialInfo socialInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        UserValidator.validateSocialRegister(socialInfo);
        return userService.registerBySocial(socialInfo, uuidHeaders.get(0));
    }
}
