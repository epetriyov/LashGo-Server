package com.lashgo.controller;

import com.lashgo.CheckConstants;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

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
    ResponseObject<RegisterResponse> register(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody LoginInfo registerInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        return new ResponseObject<>(userService.register(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0), registerInfo));
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
    ResponseObject<RegisterResponse> socialSignIn(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody SocialInfo socialInfo, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        return new ResponseObject<>(userService.socialSignIn(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0), socialInfo));
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
    ResponseObject recover(@ApiBodyObject @Valid @RequestBody String email, BindingResult result) {
        CheckUtils.handleBindingResult(logger, result);
        userService.sendRecoverPassword(email);
        return new ResponseObject<>();
    }

    @ApiMethod(
            path = Path.Users.MY_PHOTOS,
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
    @RequestMapping(value = Path.Users.MY_PHOTOS, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseList<PhotoDto> getMyPhotos(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new ResponseList<>(userService.getPhotos(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
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
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),

    })
    @RequestMapping(value = Path.Users.PHOTOS, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseList<PhotoDto> getUserPhotos(@RequestHeader HttpHeaders httpHeaders,@ApiParam(name = "userId", paramType = ApiParamType.PATH) @PathVariable("userId") int userId) {
        sessionValidator.validateWithoutUnauthEx(httpHeaders);
        return new ResponseList<>(userService.getPhotos(userId));
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
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed"),
    })
    @RequestMapping(value = Path.Users.PROFILE, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<UserDto> getProfile(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "userId", paramType = ApiParamType.PATH) @PathVariable("userId") int userId) {
        sessionValidator.validateWithoutUnauthEx(httpHeaders);
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseObject<>(userService.getProfile(CollectionUtils.isEmpty(sessionId) ? null : sessionId.get(0), userId));
    }

    @ApiMethod(
            path = Path.Users.MY_PROFILE,
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
    @RequestMapping(value = Path.Users.MY_PROFILE, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<UserDto> getMyProfile(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject<>(userService.getProfile(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
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
    ResponseList<SubscriptionDto> getSubscriptions(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "userId", paramType = ApiParamType.PATH) @PathVariable("userId") int userId) {
        sessionValidator.validateWithoutUnauthEx(httpHeaders);
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseList<>(userService.getSubscriptions(CollectionUtils.isEmpty(sessionId) ? null : sessionId.get(0), userId));
    }

    @ApiMethod(
            path = Path.Users.SUBSCRIBERS,
            verb = ApiVerb.GET,
            description = "get list of user's subscribers",
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
    @RequestMapping(value = Path.Users.SUBSCRIBERS, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseList<SubscriptionDto> getSubscribers(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "userId", paramType = ApiParamType.PATH) @PathVariable("userId") int userId) {
        sessionValidator.validateWithoutUnauthEx(httpHeaders);
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseList<>(userService.getSubscribers(CollectionUtils.isEmpty(sessionId) ? null : sessionId.get(0), userId));
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
            path = Path.Users.SUBSCRIPTIONS,
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
    @RequestMapping(value = Path.Users.SUBSCRIPTIONS, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject subscribe(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody SubscribeDto subscribeDto, BindingResult result) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(logger, result);
        userService.subscribe(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), subscribeDto.getUserId());
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Users.MAIN_SCREEN_INFO,
            verb = ApiVerb.GET,
            description = "get info for display at main screen",
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
    @RequestMapping(value = Path.Users.MAIN_SCREEN_INFO, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<MainScreenInfoDto> getMainScreenInfo(@RequestHeader HttpHeaders httpHeaders, @RequestParam(value = "news_last_view") @DateTimeFormat(pattern = CheckConstants.DATE_FORMAT) Date newsLastView, @RequestParam(value = "subscriptions_last_view") @DateTimeFormat(pattern = CheckConstants.DATE_FORMAT) Date subscriptionsLastView) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject<>(userService.getMainScreenInfo(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), new UserLastViews(newsLastView, subscriptionsLastView)));
    }

    @ApiMethod(
            path = Path.Users.MY_PROFILE,
            verb = ApiVerb.PUT,
            description = "udpate user's profile",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed or user already exists"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Users.MY_PROFILE, method = RequestMethod.PUT)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject saveProfile(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody UserDto userDto, BindingResult result) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(logger, result);
        userService.updateProfile(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), userDto);
        return new ResponseObject<>();
    }

    @ApiMethod(
            path = Path.Users.AVATAR,
            verb = ApiVerb.POST,
            description = "Save user's avatar",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers validation failed or user already sent photo for this check"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Users.AVATAR, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject saveUserAvatar(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @RequestParam("avatar") MultipartFile file) {
        sessionValidator.validate(httpHeaders);
        userService.saveAvatar(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), file);
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Users.GET,
            verb = ApiVerb.GET,
            description = "get user's list",
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
    @RequestMapping(value = Path.Users.GET, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseList<SubscriptionDto> getUsers(@RequestHeader HttpHeaders httpHeaders, @RequestParam(value = "search_text", required = false, defaultValue = "") String searchText) {
        sessionValidator.validateWithoutUnauthEx(httpHeaders);
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseList<>(userService.findUsers(CollectionUtils.isEmpty(sessionId) ? null : sessionId.get(0),searchText));
    }
}
