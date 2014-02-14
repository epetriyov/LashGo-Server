package main.java.com.check.rest.dto;

import java.io.Serializable;

/**
 * Created by Eugene on 13.02.14.
 */
public class LoginInfo implements Serializable{

    public LoginInfo()
    {

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

    private String login;

    private String passwordHash;
}
