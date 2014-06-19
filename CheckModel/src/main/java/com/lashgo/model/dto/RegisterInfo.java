package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 25.02.14
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "registerInfo", description = "info for registration")
public class RegisterInfo extends LoginInfo {
    @ApiObjectField(description = "name")
    private String name;
    @ApiObjectField(description = "surname")
    private String surname;
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
    private String gender;

    public RegisterInfo() {
        super();
    }

    public RegisterInfo(String login, String passwordHash, String email) {
        super(login, passwordHash);
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
