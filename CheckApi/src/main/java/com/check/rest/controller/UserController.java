package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.*;
import main.java.com.check.rest.error.UnautharizedException;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.service.UserService;
import main.java.com.check.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Eugene on 13.02.14.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<SessionInfo> login(@Valid @RequestBody LoginInfo loginInfo, BindingResult result) throws ValidationException {
        CheckUtils.handleBindingResult(result);
        return new Response<>(userService.login(loginInfo));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    Response register(@Valid @RequestBody RegisterInfo registerInfo, BindingResult result) throws ValidationException {
        CheckUtils.handleBindingResult(result);
        userService.register(registerInfo);
        return new Response<>();
    }

    @RequestMapping(value = "/recover", method = RequestMethod.PUT)
    public
    @ResponseBody
    Response recover(@Valid @RequestBody RecoverInfo recoverInfo, BindingResult result) throws ValidationException {
        CheckUtils.handleBindingResult(result);
        userService.sendRecoverPassword(recoverInfo);
        return new Response<>();
    }
}
