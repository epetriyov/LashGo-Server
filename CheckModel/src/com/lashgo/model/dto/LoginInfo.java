package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

/**
 * Created by Eugene on 13.02.14.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "loginInfo", description = "info for login")
public class LoginInfo implements Serializable {

    @NotEmpty
    @ApiObjectField(description = "login")
    private String login;

    @NotEmpty
    @ApiObjectField(description = "hash of password")
    private String passwordHash;

    public LoginInfo() {

    }

    public LoginInfo(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
