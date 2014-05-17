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
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionValidator sessionValidator;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<SessionInfo> login(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody LoginInfo loginInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        return new Response<>(userService.login(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0), loginInfo));
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

    @RequestMapping(value = "/photos", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<PhotoDtoList> getUserPhotos(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(userService.getPhotos(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<SubscriptionDtoList> getSubscriptions(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(userService.getSubscriptions(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @RequestMapping(value = "/subscriptions/{userId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Response unsubscribe(@RequestHeader HttpHeaders httpHeaders, @PathVariable("userId") int userId) {
        sessionValidator.validate(httpHeaders);
        userService.unsubscribe(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0),userId);
        return new Response();
    }

    @RequestMapping(value = "/subscriptions/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    Response subscribe(@RequestHeader HttpHeaders httpHeaders, @PathVariable("userId") int userId) {
        sessionValidator.validate(httpHeaders);
        userService.subscribe(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0),userId);
        return new Response();
    }
}
