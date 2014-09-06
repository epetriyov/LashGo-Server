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
    ResponseList<CheckDto> getChecks(@RequestHeader HttpHeaders httpHeaders) {
        List<String> sessionHeader = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        return new ResponseList<>(checkService.getChecks(!CollectionUtils.isEmpty(sessionHeader) ? sessionHeader.get(0) : null));
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
    ResponseList<PhotoDto> getCheckPhotos(@ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") long checkId) {
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
    ResponseList<CommentDto> getCheckComments(@ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") long checkId) {
        return new ResponseList<>(commentService.getCheckComments(checkId));
    }

    @ApiMethod(
            path = Path.Checks.COMMENTS,
            verb = ApiVerb.POST,
            description = "Add comment for check",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = CheckApiHeaders.UUID, description = "Unique identifier of client"),
            @ApiHeader(name = CheckApiHeaders.CLIENT_TYPE, description = "Type of client (ANDROID, IOS)"),
            @ApiHeader(name = CheckApiHeaders.SESSION_ID, description = "User's session identifier")
    })
    @ApiErrors(apierrors = {
            @ApiError(code = "400", description = "Headers or request body  validation failed"),
            @ApiError(code = "401", description = "Session is empty, wrong or expired")
    })
    @RequestMapping(value = Path.Checks.COMMENTS, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject addCheckComment(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") long checkId, @ApiBodyObject @Valid @RequestBody CommentDto commentDto, BindingResult result) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(logger, result);
        commentService.addCheckComment(checkId, commentDto);
        return new ResponseObject();
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
    ResponseObject saveCheckPhoto(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId, @ApiBodyObject @RequestParam("photo") MultipartFile file) {
        sessionValidator.validate(httpHeaders);
        photoService.savePhoto(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), checkId, file);
        return new ResponseObject();
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
    ResponseObject<VotePhotosResult> getVotePhoto(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "checkId", paramType = ApiParamType.PATH) @PathVariable("checkId") int checkId, @RequestParam(value = "is_count_included", required = false, defaultValue = "false") boolean isCountIncluded) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject<>(checkService.getVotePhotos(checkId, httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), isCountIncluded));
    }

    @ApiMethod(
            path = Path.Checks.LIKE,
            verb = ApiVerb.POST,
            description = "like photo",
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
    @RequestMapping(value = Path.Checks.LIKE, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<Boolean> likePhoto(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @RequestBody Integer checkId) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject<>(checkService.likeCheck(checkId, httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }
}
