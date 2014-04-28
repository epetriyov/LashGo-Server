package main.java.com.check.rest.controller;

import com.check.model.dto.CheckDto;
import com.check.model.dto.CheckDtoList;
import com.check.model.dto.Response;
import main.java.com.check.service.CheckService;
import main.java.com.check.service.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
