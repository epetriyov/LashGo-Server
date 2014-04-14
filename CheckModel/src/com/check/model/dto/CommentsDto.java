package com.check.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
public class CommentsDto implements Serializable {

    private List<CommentDto> commentDtoList;

    public CommentsDto() {
    }

    public CommentsDto(List<CommentDto> photoCommentDtoList) {
        this.commentDtoList = photoCommentDtoList;
    }

    public List<CommentDto> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(List<CommentDto> photoCommentDtoList) {
        this.commentDtoList = photoCommentDtoList;
    }
}
