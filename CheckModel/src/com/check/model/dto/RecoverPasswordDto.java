package com.check.model.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 03.03.14
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class RecoverPasswordDto implements Serializable {

    private String login;

    public RecoverPasswordDto() {
    }

    public RecoverPasswordDto(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
