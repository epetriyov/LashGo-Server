package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 06.03.2015.
 */
@ApiObject(name = "commentInfo")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CommentInfo implements Serializable {

    @Size(min = 1, max = 500)
    @ApiObjectField(description = "comment")
    private String comment;

    public CommentInfo() {
    }

    public CommentInfo(String comment) {

        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "comment='" + comment + '\'' +
                '}';
    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
