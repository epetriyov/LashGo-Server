package main.java.com.lashgo.controller;

import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.dto.*;
import main.java.com.lashgo.service.CommentService;
import main.java.com.lashgo.service.SessionValidator;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
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
@Api(name = "comment services", description = "methods for managing comments")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private SessionValidator sessionValidator;

    @ApiMethod(
            path = Path.Comments.DELETE,
            verb = ApiVerb.DELETE,
            description = "Delete comment by id",
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
    @RequestMapping(value = Path.Comments.DELETE, method = RequestMethod.DELETE)
    public
    @ResponseBody
    @ApiResponseObject
    ResponseObject deleteComment(@RequestHeader HttpHeaders httpHeaders, @ApiParam(name = "commentId", paramType = ApiParamType.PATH) @PathVariable("commentId") long commentId) {
        sessionValidator.validate(httpHeaders);
        commentService.deleteComment(commentId);
        return new ResponseObject();
    }

}
