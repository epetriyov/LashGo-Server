package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 05.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "photo")
public class PhotoDto implements Serializable {
    @Min(1)
    @ApiObjectField(description = "identifier")
    private long id;
    @Size(min = 1)
    @ApiObjectField(description = "url of photo")
    private String url;
    @NotNull
    @ApiObjectField(description = "check")
    private CheckDto check;
    @NotNull
    @ApiObjectField(description = "user")
    private UserDto user;
    @ApiObjectField(description = "is photo banned")
    private boolean isBanned;
    @ApiObjectField(description = "is photo winner")
    private boolean isWinner;
    @ApiObjectField(description = "count of user's likes")
    @Min(0)
    private int likesCount;
    @ApiObjectField(description = "count of user's comments")
    @Min(0)
    private int commentsCount;

    public PhotoDto() {

    }

    public PhotoDto(long id, String url, CheckDto check, UserDto user) {
        this.id = id;
        this.url = url;
        this.check = check;
        this.user = user;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public CheckDto getCheck() {
        return check;
    }

    public void setCheck(CheckDto check) {
        this.check = check;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
