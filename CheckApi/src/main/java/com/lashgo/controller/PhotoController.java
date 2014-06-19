package com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.Path;
import com.lashgo.model.dto.CommentDto;
import com.lashgo.model.dto.ResponseList;
import com.lashgo.model.dto.ResponseObject;
import com.lashgo.CheckConstants;
import com.lashgo.service.CommentService;
import com.lashgo.service.PhotoService;
import com.lashgo.service.SessionValidator;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * Created by Eugene on 28.04.2014.
 */
@Controller
@Api(name = "photo services", description = "methods for managing photos")
public class PhotoController extends BaseController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private SessionValidator sessionValidator;

    @Autowired
    private CommentService commentService;

    @ApiMethod(
            path = Path.Photos.GET_FILE,
            verb = ApiVerb.GET,
            description = "Gets photo stream",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE},
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
    @RequestMapping(value = Path.Photos.GET_FILE, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseEntity<FileSystemResource> getFile(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "fileName", paramType = ApiParamType.PATH) @PathVariable("fileName") String fileName) {
//        sessionValidator.validate(httpHeaders);
        logger.debug("Get photo request validated");
        FileSystemResource resource = new FileSystemResource(new File(CheckConstants.PHOTOS_FOLDER, fileName));
        logger.debug("Resource get {}", resource.getPath());
        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<>(resource, HttpStatus.OK);
        return responseEntity;
    }

    @ApiMethod(
            path = Path.Photos.VOTE,
            verb = ApiVerb.PUT,
            description = "vote for photo",
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
    @RequestMapping(value = Path.Photos.VOTE, method = RequestMethod.PUT)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject ratePhoto(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId) {
        sessionValidator.validate(httpHeaders);
        photoService.ratePhoto(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), photoId);
        return new ResponseObject();
    }

    @ApiMethod(
            path = Path.Photos.COMMENTS,
            verb = ApiVerb.GET,
            description = "get list of photo's comments",
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
    @RequestMapping(value = Path.Photos.COMMENTS, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseList<CommentDto> getPhotoComments(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId) {
//        sessionValidator.validate(httpHeaders);
        return new ResponseList<>(commentService.getPhotoComments(photoId));
    }

    @ApiMethod(
            path = Path.Photos.COMMENTS,
            verb = ApiVerb.POST,
            description = "add comment to photo",
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
    @RequestMapping(value = Path.Photos.COMMENTS, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject addPhotoComment(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId, @ApiBodyObject @RequestBody CommentDto commentDto) {
        sessionValidator.validate(httpHeaders);
        commentService.addPhotoComment(photoId, commentDto);
        return new ResponseObject();
    }
}
