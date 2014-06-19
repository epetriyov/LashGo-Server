package com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.Path;
import com.lashgo.model.dto.*;
import com.lashgo.service.SessionValidator;
import com.lashgo.service.UserService;
import com.lashgo.utils.CheckUtils;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Eugene on 13.02.14.
 */
@Controller
@Api(name = "user services", description = "methods for managing users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionValidator sessionValidator;

    @ApiMethod(
            path = Path.Users.LOGIN,
            verb = ApiVerb.POST,
            description = "login, returns sessioninfo",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed or user not found"),
    })
    @RequestMapping(value = Path.Users.LOGIN, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<SessionInfo> login(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody LoginInfo loginInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        return new ResponseObject<>(userService.login(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0), loginInfo));
    }

    @ApiMethod(
            path = Path.Users.REGISTER,
            verb = ApiVerb.POST,
            description = "create account",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed or user already exists"),
    })
    @RequestMapping(value = Path.Users.REGISTER, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject register(@ApiBodyObject @Valid @RequestBody LoginInfo registerInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        userService.register(registerInfo);
        return new ResponseObject<>();
    }

    @ApiMethod(
            path = Path.Users.SOCIAL_SIGN_IN,
            verb = ApiVerb.POST,
            description = "social sign in",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed or user email is needed during registration"),
    })
    @RequestMapping(value = Path.Users.SOCIAL_SIGN_IN, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<SessionInfo> socialSignIn(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody SocialInfo socialInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        return new ResponseObject<>(userService.socialSignIn(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0), socialInfo));
    }

    @ApiMethod(
            path = Path.Users.SOCIAL_SIGN_UP,
            verb = ApiVerb.POST,
            description = "social sign up",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),
    })
    @RequestMapping(value = Path.Users.SOCIAL_SIGN_UP, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<SessionInfo> confirmSocialSignUp(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody ExtendedSocialInfo socialInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        return new ResponseObject<>(userService.socialSignUp(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0), socialInfo));
    }

    @ApiMethod(
            path = Path.Users.RECOVER,
            verb = ApiVerb.PUT,
            description = "recover password",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed or user already exists"),
    })
    @RequestMapping(value = Path.Users.RECOVER, method = RequestMethod.PUT)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject recover(@ApiBodyObject @Valid @RequestBody RecoverInfo recoverInfo, BindingResult result) {
        logger.debug("Recover password");
        CheckUtils.handleBindingResult(logger, result);
        userService.sendRecoverPassword(recoverInfo);
        return new ResponseObject<>();
    }

    @ApiMethod(
            path = Path.Users.PROFILE,
            verb = ApiVerb.GET,
            description = "get user's profile, returns userDto",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Users.PROFILE, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<UserDto> getProfile(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject<>(userService.getProfile(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @ApiMethod(
            path = Path.Users.PHOTOS,
            verb = ApiVerb.GET,
            description = "get list of user's photos",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Users.PHOTOS, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseList<PhotoDto> getUserPhotos(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new ResponseList<>(userService.getPhotos(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @ApiMethod(
            path = Path.Users.SUBSCRIPTIONS,
            verb = ApiVerb.GET,
            description = "get list of user's subscriptions",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Users.SUBSCRIPTIONS, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseList<SubscriptionDto> getSubscriptions(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new ResponseList<>(userService.getSubscriptions(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @ApiMethod(
            path = Path.Users.SUBSCRIPTION,
            verb = ApiVerb.DELETE,
            description = "unsubscribe from user",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Users.SUBSCRIPTION, method = RequestMethod.DELETE)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject unsubscribe(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "userId", paramType = ApiParamType.PATH) @PathVariable("userId") int userId) {
        sessionValidator.validate(httpHeaders);
        userService.unsubscribe(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), userId);
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Users.SUBSCRIPTION,
            verb = ApiVerb.POST,
            description = "subscribe to user",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Users.SUBSCRIPTION, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject subscribe(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "userId", paramType = ApiParamType.PATH) @PathVariable("userId") int userId) {
        sessionValidator.validate(httpHeaders);
        userService.subscribe(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), userId);
        return new ResponseObject();
    }
}
