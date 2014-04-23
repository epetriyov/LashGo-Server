package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Eugene on 07.04.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RecoverInfo implements Serializable{

    private String login;

    public RecoverInfo() {
    }

    public RecoverInfo(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
