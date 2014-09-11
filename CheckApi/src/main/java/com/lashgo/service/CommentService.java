package com.lashgo.service;

import com.lashgo.model.dto.CommentDto;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
public interface CommentService {

    List<CommentDto> getCheckComments(int checkId);

    List<CommentDto> getPhotoComments(long photoId);

    CommentDto addCheckComment(String sessionId,int checkId, String commentText);

    CommentDto addPhotoComment(String sessionId,long photoId, String commentText);

    void deleteComment(long commentId);
}
