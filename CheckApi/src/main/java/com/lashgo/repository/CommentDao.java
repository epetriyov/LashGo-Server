package com.lashgo.repository;

import com.lashgo.model.dto.CommentDto;
import com.lashgo.domain.Comments;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
public interface CommentDao {

    List<CommentDto> getCommentsByCheck(int checkId);

    CommentDto addCheckComment(int userId, int checkId, String commentText, Date commentDate);

    void deleteComment(long commentId);

    List<CommentDto> getCommentsByPhoto(long photoId);

    CommentDto addPhotoComment(int userId, long photoId, String commentText, Date commentDate);
}
