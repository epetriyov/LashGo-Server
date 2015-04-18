package com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.Path;
import com.lashgo.model.dto.EventDto;
import com.lashgo.model.dto.ResponseList;
import com.lashgo.service.EventService;
import com.lashgo.service.SessionValidator;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Eugene on 19.10.2014.
 */
@Controller
@Api(name = "events services", description = "methods for managing events")
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;

    @Autowired
    private SessionValidator sessionValidator;

    @ApiMethod(
            path = Path.Events.GET,
            verb = ApiVerb.GET,
            description = "Gets list of user's events",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequestMapping(value = Path.Events.GET, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<EventDto> getEvents(@RequestHeader HttpHeaders httpHeaders,@RequestParam(value = "subscriptions", required = false, defaultValue = "false") boolean subscriptions) {
        sessionValidator.validate(httpHeaders);
        List<String> sessionHeader = httpHeaders.get(CheckApiHeaders.session_id.name());
        return new ResponseList<>(eventService.getEvents(!CollectionUtils.isEmpty(sessionHeader) ? sessionHeader.get(0) : null, subscriptions));
    }
}
