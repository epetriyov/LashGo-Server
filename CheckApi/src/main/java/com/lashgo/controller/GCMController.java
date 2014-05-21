package main.java.com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.dto.GcmRegistrationDto;
import com.lashgo.model.dto.ResponseObject;
import main.java.com.lashgo.service.GcmService;
import main.java.com.lashgo.service.SessionValidator;
import main.java.com.lashgo.utils.CheckUtils;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.03.14
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Api(name = "gcm services", description = "methods for managing gcm")
public class GCMController extends BaseController {

    @Autowired
    private GcmService gcmService;

    @Autowired
    private SessionValidator sessionValidator;

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
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Gcm.REGISTER, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject registerDevice(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody GcmRegistrationDto registrationDto, BindingResult result) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(logger, result);
        gcmService.addRegistrationId(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), registrationDto);
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Gcm.TEST,
            verb = ApiVerb.POST,
            description = "send current check to all devices through gcm",
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
    void sendMultiCast() {
        gcmService.sendChecks();
    }
}
