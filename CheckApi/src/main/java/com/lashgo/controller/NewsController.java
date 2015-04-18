package com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.Path;
import com.lashgo.model.dto.NewsDto;
import com.lashgo.model.dto.ResponseList;
import com.lashgo.service.ContentService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eugene on 06.05.2014.
 */
@Controller
@Api(name = "content services", description = "methods for managing contents")
public class NewsController extends BaseController {

    @Autowired
    private ContentService contentService;

    @ApiMethod(
            path = Path.Contents.GET,
            verb = ApiVerb.GET,
            description = "get contents list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequestMapping(value = Path.Contents.GET, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<NewsDto> getNews() {
        return new ResponseList<>(contentService.getNews());
    }
}
