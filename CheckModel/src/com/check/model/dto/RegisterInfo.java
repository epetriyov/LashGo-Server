package com.check.model.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 25.02.14
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public class RegisterInfo extends LoginInfo {

    private String email;

    public RegisterInfo() {
        super();
    }

    public RegisterInfo(String login, String passwordHash, String email) {
        super(login, passwordHash);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
