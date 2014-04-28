package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 14.04.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDto implements Serializable {

    private int id;

    private String login;

    private String name;

    private String surname;

    private String about;

    private String city;

    private Date birthDate;

    private String avatar;
    private String email;

    public UserDto() {
    }

    public UserDto(int id, String login, String name, String surname, String about, String city, Date birthDate, String avatar, String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.about = about;
        this.city = city;
        this.birthDate = birthDate;
        this.avatar = avatar;
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
