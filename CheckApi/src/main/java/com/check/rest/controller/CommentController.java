package main.java.com.check.rest.controller;

import com.check.model.dto.*;
import main.java.com.check.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Eugene on 14.04.2014.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/checks/{checkId}/comments", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CommentsDto> getCheckComments(@PathVariable("checkId") long checkId) {
        return new Response<>(commentService.getCheckComments(checkId));
    }

    @RequestMapping(value = "/photos/{photoId}/comments", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CommentsDto> getPhotoComments(@PathVariable("photoId") long photoId) {
        return new Response<>(commentService.getPhotoComments(photoId));
    }

    @RequestMapping(value = "/checks/{checkId}/comments", method = RequestMethod.POST)
    public
    @ResponseBody
    Response addCheckComment(@PathVariable("checkId") long checkId, @RequestBody CommentDto commentDto) {
        commentService.addCheckComment(checkId, commentDto);
        return new Response();
    }

    @RequestMapping(value = "/photos/{photoId}/comments", method = RequestMethod.POST)
    public
    @ResponseBody
    Response addPhotoComment(@PathVariable("photoId") long photoId, @RequestBody CommentDto commentDto) {
        commentService.addPhotoComment(photoId, commentDto);
        return new Response();
    }

    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Response deleteComment(@PathVariable("commentId") long commentId) {
        commentService.deleteComment(commentId);
        return new Response();
    }

}
