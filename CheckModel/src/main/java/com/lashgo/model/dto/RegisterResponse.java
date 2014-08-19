package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 06.08.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "LoginResponse", description = "response of login")
public class RegisterResponse implements Serializable {

    @Min(1)
    private long userId;
    @Size(min = 1)
    @ApiObjectField(description = "username")
    private String userName;
    @Size(min = 1)
    @ApiObjectField(description = "avatar")
    private String avatar;
    @Min(0)
    @ApiObjectField(description = "count of subscribes")
    private int subscribesCount;
    @Min(0)
    @ApiObjectField(description = "count of subscriberes")
    private int subscribersCount;
    @Size(min = 1)
    @ApiObjectField(description = "identifier of session")
    private String sessionId;

    public RegisterResponse(long userId, String sessionId, String userName, String avatar, int subscribesCount, int subscribersCount) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.userName = userName;
        this.avatar = avatar;
        this.subscribesCount = subscribesCount;
        this.subscribersCount = subscribersCount;
    }

    public RegisterResponse() {

    }

    public RegisterResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSubscribesCount() {
        return subscribesCount;
    }

    public void setSubscribesCount(int subscribesCount) {
        this.subscribesCount = subscribesCount;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public void setSubscribersCount(int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }
}
