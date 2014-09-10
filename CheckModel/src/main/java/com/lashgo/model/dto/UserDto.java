package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 14.04.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "user")
public class UserDto implements Serializable {
    @Min(1)
    @ApiObjectField(description = "identifier")
    private int id;
    @Size(min = 1)
    @ApiObjectField(description = "login")
    private String login;
    @ApiObjectField(description = "fio")
    private String fio;
    @ApiObjectField(description = "about")
    private String about;
    @ApiObjectField(description = "city")
    private String city;
    @ApiObjectField(description = "birthDate")
    private Date birthDate;
    @ApiObjectField(description = "avatar")
    private String avatar;
    @Size(min = 1)
    @ApiObjectField(description = "email")
    private String email;
    @ApiObjectField(description = "passwordHash")
    private String passwordHash;
    @ApiObjectField(description = "count of user's subscribes")
    private int userSubscribes;
    @ApiObjectField(description = "count of user's subscribers")
    private int userSubscribers;
    @ApiObjectField(description = "count of user's checks")
    private int checksCount;
    @ApiObjectField(description = "count of user's comments")
    private int commentsCount;
    @ApiObjectField(description = "count of user's likes")
    private int likesCount;
    public UserDto() {
    }
    public UserDto(int id, String login, String fio, String about, String city, Date birthDate, String avatar, String email) {
        this.id = id;
        this.login = login;
        this.fio = fio;
        this.about = about;
        this.city = city;
        this.birthDate = birthDate;
        this.avatar = avatar;
        this.email = email;
    }

    public UserDto(int id, String login, String avatar) {
        this.id = id;
        this.login = login;
        this.avatar = avatar;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getUserSubscribes() {
        return userSubscribes;
    }

    public void setUserSubscribes(int userSubscribes) {
        this.userSubscribes = userSubscribes;
    }

    public int getUserSubscribers() {
        return userSubscribers;
    }

    public void setUserSubscribers(int userSubscribers) {
        this.userSubscribers = userSubscribers;
    }

    public int getChecksCount() {
        return checksCount;
    }

    public void setChecksCount(int checksCount) {
        this.checksCount = checksCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
