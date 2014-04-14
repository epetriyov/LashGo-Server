package main.java.com.check.service;

import com.check.model.dto.*;

/**
 * Created by Eugene on 14.04.2014.
 */
public interface CommentService {

    CommentsDto getCheckComments(long checkId);

    CommentsDto getPhotoComments(long photoId);

    void addCheckComment(long checkId, CommentDto commentDto);

    void addPhotoComment(long photoId, CommentDto commentDto);

    void deleteComment(long commentId);
}
