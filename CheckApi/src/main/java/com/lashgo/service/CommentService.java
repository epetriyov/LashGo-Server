package com.lashgo.service;

import com.lashgo.model.dto.CommentDto;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
public interface CommentService {

    List<CommentDto> getCheckComments(long checkId);

    List<CommentDto> getPhotoComments(long photoId);

    void addCheckComment(long checkId, CommentDto commentDto);

    void addPhotoComment(long photoId, CommentDto commentDto);

    void deleteComment(long commentId);
}
