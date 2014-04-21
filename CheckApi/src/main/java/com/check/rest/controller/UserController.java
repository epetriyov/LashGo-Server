package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.*;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.UnautharizedException;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.service.SessionValidator;
import main.java.com.check.service.UserService;
import main.java.com.check.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionValidator sessionValidator;

    private static Logger logger = LoggerFactory.getLogger("FILE");

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<SessionInfo> login(@Valid @RequestBody LoginInfo loginInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        return new Response<>(userService.login(loginInfo));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    Response register(@Valid @RequestBody RegisterInfo registerInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        userService.register(registerInfo);
        return new Response<>();
    }

    @RequestMapping(value = "/recover", method = RequestMethod.PUT)
    public
    @ResponseBody
    Response recover(@Valid @RequestBody RecoverInfo recoverInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        userService.sendRecoverPassword(recoverInfo);
        return new Response<>();
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<UserDto> getProfile(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(userService.getProfile(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }
}
