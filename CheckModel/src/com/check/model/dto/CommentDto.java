package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 14.04.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CommentDto implements Serializable {

    @Min(1)
    private long id;
    @NotEmpty
    private String content;
    @NotNull
    private Date createDate;
    @NotNull
    private UserDto user;

    public CommentDto() {
    }

    public CommentDto(long id, String content, Date createDate, UserDto user) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
