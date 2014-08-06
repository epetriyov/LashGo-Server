package com.lashgo.model.dto;

import java.io.Serializable;

/**
 * Created by Eugene on 06.08.2014.
 */
public class RegisterResponse implements Serializable {

    private String userName;

    private String avatar;

    private int subscribesCount;

    private int subscribersCount;

    public RegisterResponse(String userName, String avatar, int subscribesCount, int subscribersCount) {
        this.userName = userName;
        this.avatar = avatar;
        this.subscribesCount = subscribesCount;
        this.subscribersCount = subscribersCount;
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
