package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 17.02.14.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "sessionInfo", description = "id of session")
public class SessionInfo implements Serializable {

    @Size(min = 1)
    @ApiObjectField(description = "identifier of session")
    private String sessionId;
    @Min(1)
    private long userId;

    public SessionInfo() {

    }

    public SessionInfo(String sessionId, long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
