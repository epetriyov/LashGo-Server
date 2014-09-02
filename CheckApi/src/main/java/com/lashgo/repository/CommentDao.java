package com.lashgo.repository;

import com.lashgo.model.dto.CommentDto;
import com.lashgo.domain.Comments;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
public interface CommentDao {

    List<CommentDto> getCommentsByCheck(long checkId);

    void addCheckComment(long checkId, CommentDto checkCommentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentsByPhoto(long photoId);

    void addPhotoComment(long photoId, CommentDto photoCommentDto);
}
