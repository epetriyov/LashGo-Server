package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.*;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.service.UserService;
import main.java.com.check.rest.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    Response<SessionInfo> login(@RequestHeader HttpHeaders requestHeaders, @RequestBody LoginInfo loginInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        UserValidator.validateLogin(loginInfo);
        return new Response<>(userService.login(loginInfo, uuidHeaders.get(0)));
    }

    @RequestMapping("/register")
    public
    @ResponseBody
    Response<SessionInfo> register(@RequestHeader HttpHeaders requestHeaders, @RequestBody RegisterInfo registerInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        UserValidator.validateRegsiter(registerInfo);
        return new Response<>(userService.register(registerInfo, uuidHeaders.get(0)));
    }

    @RequestMapping("/social-sign-in")
    public
    @ResponseBody
    Response<SessionInfo> signInWithSocial(@RequestHeader HttpHeaders requestHeaders, @RequestBody SocialInfo socialInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        UserValidator.validateSocialRegister(socialInfo);
        SessionInfo sessionInfo = userService.registerBySocial(socialInfo, uuidHeaders.get(0));
        if (sessionInfo == null) {
            throw new Exception(ErrorCodes.EMPTY_EMAIL);
        }
        return new Response<>(sessionInfo);
    }
}
