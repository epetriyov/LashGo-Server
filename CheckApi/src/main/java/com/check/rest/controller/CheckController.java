package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.CheckDto;
import com.check.model.dto.CheckDtoList;
import com.check.model.dto.PhotoDtoList;
import com.check.model.dto.Response;
import main.java.com.check.service.CheckService;
import main.java.com.check.service.SessionValidator;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Eugene on 14.04.2014.
 */
@Controller
@Api(name = "checks", description = "methods for managing checks")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private SessionValidator sessionValidator;

    @ApiMethod(
            path="/checks",
            verb= ApiVerb.GET,
            description="Gets a city with the given name, provided that the name is between sydney, melbourne and perth",
            produces={MediaType.APPLICATION_JSON_VALUE},
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
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

    @RequestMapping(value = "/checks/{checkId}/photos", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<PhotoDtoList> getCheckPhotos(@RequestHeader HttpHeaders httpHeaders, @PathVariable("checkId") long checkId) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(checkService.getPhotos(checkId));
    }
}
