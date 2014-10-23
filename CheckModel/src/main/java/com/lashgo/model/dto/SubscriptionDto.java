package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 05.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "subscription", description = "subscription of user (user to watch for)")
public class SubscriptionDto implements Serializable {

    @Min(1)
    @ApiObjectField(description = "identifier")
    private int id;
    @Min(1)
    @ApiObjectField(description = "identifier of user")
    private int userId;

    @ApiObjectField(description = "avatar url")
    private String userAvatar;
    @Size(min = 1)
    @ApiObjectField(description = "login")
    private String userLogin;
    @ApiObjectField(description = "subscribe_date")
    private Date subscriptionDate;
    @Size(min = 1)
    @ApiObjectField(description = "fio")
    private String fio;

    @ApiObjectField(description = "true if current user is subscribed to subscription")
    private boolean amISubscribed = true;

    public SubscriptionDto() {
    }

    public SubscriptionDto(int id, int userId, String userAvatar, String userLogin, Date subscriptionDate) {
        this.id = id;
        this.userId = userId;
        this.userAvatar = userAvatar;
        this.userLogin = userLogin;
        this.subscriptionDate = subscriptionDate;
    }

    public boolean isAmISubscribed() {
        return amISubscribed;
    }

    public void setAmISubscribed(boolean amISubscribed) {
        this.amISubscribed = amISubscribed;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
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

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
