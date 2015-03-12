package com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.Path;
import com.lashgo.model.dto.ApnTokenDto;
import com.lashgo.model.dto.GcmRegistrationDto;
import com.lashgo.model.dto.ResponseObject;
import com.lashgo.service.ApnsService;
import com.lashgo.service.GcmService;
import com.lashgo.service.NotificationService;
import com.lashgo.service.SessionValidator;
import com.lashgo.utils.CheckUtils;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.03.14
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Api(name = "push notification services", description = "methods for push notifications")
public class PushNotificationsController extends BaseController {

    @Autowired
    private SessionValidator sessionValidator;

    @Autowired
    private GcmService gcmService;

    @Autowired
    private ApnsService apnsService;

    @Autowired
    private NotificationService notificationService;

    @ApiMethod(
            path = Path.Gcm.REGISTER,
            verb = ApiVerb.POST,
            description = "save gcm registration id",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers or request body validation failed"),
    })
    @RequestMapping(value = Path.Gcm.REGISTER, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject registerGcmToken(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody GcmRegistrationDto registrationDto, BindingResult result) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(result);
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        String session = !CollectionUtils.isEmpty(sessionId) ? sessionId.get(0) : null;
        gcmService.addRegistrationId(session, registrationDto);
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Apns.REGISTER,
            verb = ApiVerb.POST,
            description = "save apns token",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers or request body validation failed"),
    })
    @RequestMapping(value = Path.Apns.REGISTER, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject registerApnToken(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody ApnTokenDto apnTokenDto, BindingResult result) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(result);
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        String session = !CollectionUtils.isEmpty(sessionId) ? sessionId.get(0) : null;
        apnsService.addToken(session, apnTokenDto);
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Gcm.TEST,
            verb = ApiVerb.POST,
            description = "send gcm to device with specified token",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers or request body validation failed")
    })
    @RequestMapping(value = Path.Gcm.TEST, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject testGcm(@ApiBodyObject @Valid @RequestBody GcmRegistrationDto gcmRegistrationDto, BindingResult result) {
        CheckUtils.handleBindingResult(result);
        gcmService.sendGcm(gcmRegistrationDto.getRegistrationId());
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Apns.TEST,
            verb = ApiVerb.POST,
            description = "send apn to device with specified token",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers or request body validation failed")
    })
    @RequestMapping(value = Path.Apns.TEST, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject testApns(@ApiBodyObject @Valid @RequestBody ApnTokenDto apnTokenDto, BindingResult result) {
        CheckUtils.handleBindingResult(result);
        apnsService.sendApn(apnTokenDto.getToken());
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Notifications.GCM_SEND,
            verb = ApiVerb.GET,
            description = "send active check through gcm",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers or request body validation failed")
    })
    @RequestMapping(value = Path.Notifications.GCM_SEND, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject sendGcm() {
        notificationService.sendGcm();
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Notifications.APNS_SEND,
            verb = ApiVerb.GET,
            description = "send active check through apns",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers or request body validation failed")
    })
    @RequestMapping(value = Path.Notifications.APNS_SEND, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject sendApn() {
        notificationService.sendApn();
        return new ResponseObject();
    }
}
