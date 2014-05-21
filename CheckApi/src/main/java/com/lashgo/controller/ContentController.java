package main.java.com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.dto.ContentDto;
import com.lashgo.model.dto.ResponseList;
import main.java.com.lashgo.service.ContentService;
import main.java.com.lashgo.service.SessionValidator;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eugene on 06.05.2014.
 */
@Controller
@Api(name = "content services", description = "methods for managing contents")
public class ContentController extends BaseController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private SessionValidator sessionValidator;

    @ApiMethod(
            path = Path.Contents.GET,
            verb = ApiVerb.GET,
            description = "get contents list",
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
    @RequestMapping(value = Path.Contents.GET, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<ContentDto> getNews(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new ResponseList<>(contentService.getNews(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0)));
    }
}
