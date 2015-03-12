package com.lashgo.service;

import com.lashgo.model.dto.CommentDto;
import com.lashgo.model.dto.CommentInfo;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
public interface CommentService {

    List<CommentDto> getCheckComments(int checkId);

    List<CommentDto> getPhotoComments(long photoId);

    @Deprecated
    CommentDto addPhotoComment(String sessionId,long photoId, String commentText);

    CommentDto addPhotoComment(String sessionId,long photoId, CommentInfo commentInfo);

    void deleteComment(long commentId);
}
