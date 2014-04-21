package main.java.com.check.rest.controller;

import com.check.model.dto.CheckDto;
import com.check.model.dto.CheckDtoList;
import com.check.model.dto.Response;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.service.CheckService;
import main.java.com.check.service.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Eugene on 14.04.2014.
 */
@Controller
public class CheckController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private SessionValidator sessionValidator;

    @RequestMapping(value = "/checks", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CheckDtoList> getChecks(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(checkService.getChecks());
    }

    @RequestMapping(value = "/current-check", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CheckDto> getCurrentCheck(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(checkService.getCurrentCheck());
    }
}
