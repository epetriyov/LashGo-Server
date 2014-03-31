package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.GcmRegistrationDto;
import com.check.model.dto.Response;
import com.check.model.dto.MulticastResult;
import main.java.com.check.rest.error.GcmSendException;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.service.GcmService;
import main.java.com.check.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.03.14
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class GCMController {

    @Autowired
    private GcmService gcmService;

    @RequestMapping(value = "/gcm-register", method = RequestMethod.POST)
    public
    @ResponseBody
    Response registerDevice(@RequestHeader HttpHeaders httpHeaders, @Validated @RequestBody GcmRegistrationDto registrationDto, BindingResult result) throws ValidationException {
        CheckUtils.handleBindingResult(result);
        gcmService.addRegistrationId(httpHeaders.get(CheckApiHeaders.UUID).get(0), registrationDto);
        return new Response();
    }

    @RequestMapping(value = "/gcm-test", method = RequestMethod.POST)
    public
    @ResponseBody
    void sendMultiCast() {
        gcmService.sendChecks();
    }
}
