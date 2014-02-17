package com.check.model.dto;

import java.io.Serializable;

/**
 * Created by Eugene on 17.02.14.
 */
public class SessionInfo implements Serializable {

    private String sessionId;

    public SessionInfo() {

    }

    public SessionInfo(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
