package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 25.08.2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiObject(name = "votePhoto", description = "photo for vote")
public class VotePhoto implements Serializable {

    @Size(min = 1)
    @ApiObjectField(description = "photoUrl")
    private String photoUrl;

    @Min(1)
    @ApiObjectField(description = "userId")
    private int userId;

    @Size(min = 1)
    @ApiObjectField(description = "userAvatar")
    private String userAvatar;

    @Size(min = 1)
    @ApiObjectField(description = "userLogin")
    private String userLogin;
    @Min(1)
    @ApiObjectField(description = "photoId")
    private long photoId;

    public VotePhoto() {
    }

    public VotePhoto(String photoUrl, int userId, String userAvatar, String userLogin, long photoId) {
        this.photoUrl = photoUrl;
        this.userId = userId;
        this.userAvatar = userAvatar;
        this.userLogin = userLogin;
        this.photoId = photoId;
    }

    public String getPhotoUrl() {

        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }
}
