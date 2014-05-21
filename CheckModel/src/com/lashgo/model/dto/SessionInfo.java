package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

/**
 * Created by Eugene on 17.02.14.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "sessionInfo", description = "id of session")
public class SessionInfo implements Serializable {

    @NotEmpty
    @ApiObjectField(description = "identifier of session")
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
