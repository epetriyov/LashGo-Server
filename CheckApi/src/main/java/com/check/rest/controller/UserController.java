package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.*;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.LoginException;
import main.java.com.check.rest.error.RegisterException;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.service.UserService;
import main.java.com.check.rest.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping("/login")
    public
    @ResponseBody
    Response<SessionInfo> login(@RequestHeader HttpHeaders requestHeaders, @RequestBody LoginInfo loginInfo) throws LoginException, ValidationException {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        userValidator.validateLogin(loginInfo);
        return new Response<>(userService.login(loginInfo, uuidHeaders.get(0)));
    }

    @RequestMapping("/register")
    public
    @ResponseBody
    Response<SessionInfo> register(@RequestHeader HttpHeaders requestHeaders, @RequestBody RegisterInfo registerInfo) throws RegisterException, ValidationException {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        userValidator.validateRegsiter(registerInfo);
        return new Response<>(userService.register(registerInfo, uuidHeaders.get(0)));
    }

    @RequestMapping("/social-sign-in")
    public
    @ResponseBody
    Response<SessionInfo> signInWithSocial(@RequestHeader HttpHeaders requestHeaders, @RequestBody SocialInfo socialInfo) throws RegisterException, ValidationException {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        userValidator.validateSocialRegister(socialInfo);
        return new Response<>(userService.registerBySocial(socialInfo, uuidHeaders.get(0)));
    }
}
