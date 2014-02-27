package com.check.model.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 27.02.14
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public class EmailNeed implements Serializable {

    public EmailNeed() {

    }

    public EmailNeed(boolean emailNeeded) {
        isEmailNeeded = emailNeeded;
    }

    public boolean isEmailNeeded() {
        return isEmailNeeded;
    }

    public void setEmailNeeded(boolean emailNeeded) {
        isEmailNeeded = emailNeeded;
    }

    private boolean isEmailNeeded;
}
