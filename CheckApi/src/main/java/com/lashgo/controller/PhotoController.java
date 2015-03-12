package com.lashgo.controller;

import com.lashgo.CheckConstants;
import com.lashgo.error.PhotoReadException;
import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.Path;
import com.lashgo.model.dto.*;
import com.lashgo.service.CommentService;
import com.lashgo.service.PhotoService;
import com.lashgo.service.SessionValidator;
import com.lashgo.utils.CheckUtils;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;

/**
 * Created by Eugene on 28.04.2014.
 */
@Controller
@Api(name = "photo services", description = "methods for managing photos")
public class
        PhotoController extends BaseController {

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
            produces = {MediaType.MULTIPART_FORM_DATA_VALUE},
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
    @RequestMapping(value = Path.Photos.GET_FILE, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseEntity<FileSystemResource> getFile(@ApiParam(name = "fileName", paramType = ApiParamType.PATH) @PathVariable("fileName") String fileName) {
        if (fileName != null) {
            File file = new File(CheckConstants.PHOTOS_FOLDER, fileName);
            if (file.exists()) {
                FileSystemResource resource = new FileSystemResource(file);
                ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<>(resource, HttpStatus.OK);
                return responseEntity;
            }
        }
        throw new PhotoReadException();
    }


    @ApiMethod(
            path = Path.Photos.COUNTERS,
            verb = ApiVerb.GET,
            description = "Gets photo's counters",
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
    })
    @RequestMapping(value = Path.Photos.COUNTERS, method = RequestMethod.GET)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject<CheckCounters> getPhotoCounters(@ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId) {
        return new ResponseObject<>(photoService.getPhotoCounters(photoId));
    }

    @ApiMethod(
            path = Path.Photos.VOTE,
            verb = ApiVerb.POST,
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
    @RequestMapping(value = Path.Photos.VOTE, method = RequestMethod.POST)
    public
    @ApiResponseObject
    @ResponseBody
    ResponseObject ratePhoto(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @Valid @RequestBody VoteAction voteAction, BindingResult result) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(result);
        photoService.ratePhoto(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), voteAction);
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
    ResponseList<CommentDto> getPhotoComments(@ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId) {
        return new ResponseList<>(commentService.getPhotoComments(photoId));
    }

    @ApiMethod(
            path = Path.Photos.COMMENTS_NEW,
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
    @RequestMapping(value = Path.Photos.COMMENTS_NEW, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<CommentDto> addPhotoComment(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId, @ApiBodyObject @RequestBody @Valid CommentInfo commentInfo, BindingResult bindingResult) {
        CheckUtils.handleBindingResult(bindingResult);
        sessionValidator.validate(httpHeaders);
        return new ResponseObject(commentService.addPhotoComment(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), photoId, commentInfo));
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
    @Deprecated
    ResponseObject<CommentDto> addPhotoCommentOld(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId, @ApiBodyObject @RequestBody String comment) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject(commentService.addPhotoComment(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), photoId, comment));
    }

    @ApiMethod(
            path = Path.Photos.LIKE,
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
    @RequestMapping(value = Path.Photos.LIKE, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    @Deprecated
    ResponseObject<Boolean> oldLikePhoto(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @RequestBody Long photoId) {
        sessionValidator.validate(httpHeaders);
        return new ResponseObject<>(photoService.likePhoto(photoId, httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @ApiMethod(
            path = Path.Photos.LIKE_NEW,
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
    @RequestMapping(value = Path.Photos.LIKE_NEW, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<Boolean> likePhoto(@RequestHeader HttpHeaders httpHeaders, @ApiBodyObject @RequestBody @Valid LikedPhotoDto photoDto, BindingResult bindingResult) {
        sessionValidator.validate(httpHeaders);
        CheckUtils.handleBindingResult(bindingResult);
        return new ResponseObject<>(photoService.likePhoto(photoDto, httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0)));
    }

    @ApiMethod(
            path = Path.Photos.PHOTO,
            verb = ApiVerb.GET,
            description = "get photo by id",
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
    @RequestMapping(value = Path.Photos.PHOTO, method = RequestMethod.GET)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject<PhotoDto> getPhoto(@ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId) {
        return new ResponseObject<>(photoService.getPhotoById(photoId));
    }

    @ApiMethod(
            path = Path.Photos.COMPLAIN,
            verb = ApiVerb.POST,
            description = "make a complain for photo",
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
    @RequestMapping(value = Path.Photos.COMPLAIN, method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject complainPhoto(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "photoId", paramType = ApiParamType.PATH) @PathVariable("photoId") long photoId) {
        sessionValidator.validate(httpHeaders);
        photoService.complainPhoto(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), photoId);
        return new ResponseObject<>();
    }
}
