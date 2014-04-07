package com.check.model.dto;

import java.io.Serializable;

/**
 * Created by Eugene on 07.04.2014.
 */
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
