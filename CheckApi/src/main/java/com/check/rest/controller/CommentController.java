package main.java.com.check.rest.controller;

import com.check.model.dto.*;
import main.java.com.check.service.CommentService;
import main.java.com.check.service.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Eugene on 14.04.2014.
 */
@Controller
public class CommentController extends BaseController{

    @Autowired
    private CommentService commentService;

    @Autowired
    private SessionValidator sessionValidator;

    @RequestMapping(value = "/checks/{checkId}/comments", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CommentsDto> getCheckComments(@RequestHeader HttpHeaders httpHeaders, @PathVariable("checkId") long checkId) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(commentService.getCheckComments(checkId));
    }

    @RequestMapping(value = "/photos/{photoId}/comments", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CommentsDto> getPhotoComments(@RequestHeader HttpHeaders httpHeaders, @PathVariable("photoId") long photoId) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(commentService.getPhotoComments(photoId));
    }

    @RequestMapping(value = "/checks/{checkId}/comments", method = RequestMethod.POST)
    public
    @ResponseBody
    Response addCheckComment(@RequestHeader HttpHeaders httpHeaders, @PathVariable("checkId") long checkId, @RequestBody CommentDto commentDto) {
        sessionValidator.validate(httpHeaders);
        commentService.addCheckComment(checkId, commentDto);
        return new Response();
    }

    @RequestMapping(value = "/photos/{photoId}/comments", method = RequestMethod.POST)
    public
    @ResponseBody
    Response addPhotoComment(@RequestHeader HttpHeaders httpHeaders, @PathVariable("photoId") long photoId, @RequestBody CommentDto commentDto) {
        sessionValidator.validate(httpHeaders);
        commentService.addPhotoComment(photoId, commentDto);
        return new Response();
    }

    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Response deleteComment(@RequestHeader HttpHeaders httpHeaders, @PathVariable("commentId") long commentId) {
        sessionValidator.validate(httpHeaders);
        commentService.deleteComment(commentId);
        return new Response();
    }

}
