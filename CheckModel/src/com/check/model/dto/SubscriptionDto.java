package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Eugene on 05.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SubscriptionDto implements Serializable {

    private int id;

    private int userId;

    private String userAvatar;
    private String userLogin;

    public SubscriptionDto() {
    }

    public SubscriptionDto(int id, int userId, String userAvatar, String userLogin) {

        this.id = id;
        this.userId = userId;
        this.userAvatar = userAvatar;
        this.userLogin = userLogin;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
