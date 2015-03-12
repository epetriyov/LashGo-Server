package com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.Path;
import com.lashgo.model.dto.*;
import com.lashgo.service.CheckService;
import com.lashgo.service.CommentService;
import com.lashgo.service.PhotoService;
import com.lashgo.service.SessionValidator;
import com.lashgo.utils.CheckUtils;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@Controller
@Api(name = "check services", description = "methods for managing checks")
public class CheckController extends BaseController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private SessionValidator sessionValidator;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PhotoService photoService;

    @ApiMethod(
            path = Path.Checks.GET,
            verb = ApiVerb.GET,
            description = "Gets list of available checks",
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
    @RequestMapping(value = Path.Checks.GET, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<CheckDto> getChecks(@RequestParam(value = "search_text", required = false, defaultValue = "") String searchText, @RequestHeader HttpHeaders httpHeaders) {
        List<String> sessionHeader = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseList<>(checkService.getChecks(!CollectionUtils.isEmpty(sessionHeader) ? sessionHeader.get(0) : null, searchText));
    }

    @ApiMethod(
            path = Path.Checks.CHECK,
            verb = ApiVerb.GET,
            description = "Gets check by id",
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
    @RequestMapping(value = Path.Checks.CHECK, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject<CheckDto> getCheckById(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId) {
        List<String> sessionHeader = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseObject<>(checkService.getCheckById(!CollectionUtils.isEmpty(sessionHeader) ? sessionHeader.get(0) : null, checkId));
    }

    @ApiMethod(
            path = Path.Checks.COUNTERS,
            verb = ApiVerb.GET,
            description = "Gets check counters by check id",
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
    @RequestMapping(value = Path.Checks.COUNTERS, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject<CheckCounters> getCheckCounters(@ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId) {
        return new ResponseObject<>(checkService.getCheckCounters(checkId));
    }

    @ApiMethod(
            path = Path.Checks.PHOTOS,
            verb = ApiVerb.GET,
            description = "Gets list of check's photos",
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
    @RequestMapping(value = Path.Checks.PHOTOS, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<PhotoDto> getCheckPhotos(@ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId) {
        return new ResponseList<>(checkService.getPhotos(checkId));
    }

    @ApiMethod(
            path = Path.Checks.COMMENTS,
            verb = ApiVerb.GET,
            description = "Gets list of check's comments",
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
    @RequestMapping(value = Path.Checks.COMMENTS, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<CommentDto> getCheckComments(@ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId) {
        return new ResponseList<CommentDto>(commentService.getCheckComments(checkId));
    }

    @ApiMethod(
            path = Path.Checks.USERS,
            verb = ApiVerb.GET,
            description = "Gets list of check's users",
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
    @RequestMapping(value = Path.Checks.USERS, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<SubscriptionDto> getCheckUsers(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId) {
        List<String> sessionHeader = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseList<SubscriptionDto>(checkService.getCheckUsers(!CollectionUtils.isEmpty(sessionHeader) ? sessionHeader.get(0) : null, checkId));
    }

    @ApiMethod(
            path = Path.Checks.PHOTOS,
            verb = ApiVerb.POST,
            description = "Save photo for check",
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
    @RequestMapping(value = Path.Checks.PHOTOS, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject<PhotoPath> saveCheckPhoto(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId, @ApiBodyObject @RequestParam("photo") MultipartFile file) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject(photoService.savePhoto(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), checkId, file));
    }

    @ApiMethod(
            path = Path.Checks.VOTE_PHOTOS,
            verb = ApiVerb.GET,
            description = "get vote photos",
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
    @RequestMapping(value = Path.Checks.VOTE_PHOTOS, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseList<VotePhoto> getVotePhoto(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId) {
        sessionValidator.validate(httpHeaders);
        return new ResponseList<>(checkService.getVotePhotos(checkId, httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @ApiMethod(
            path = Path.Checks.GET,
            verb = ApiVerb.POST,
            description = "create new check",
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
    @RequestMapping(value = Path.Checks.GET, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject createCheck(@ApiBodyObject @Valid @RequestBody CheckDto checkDto, BindingResult result) {
        CheckUtils.handleBindingResult(result);
        checkService.createNewCheck(checkDto);
        return new ResponseObject();
    }
}
