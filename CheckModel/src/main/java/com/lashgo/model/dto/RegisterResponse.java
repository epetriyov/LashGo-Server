package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 06.08.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "LoginResponse", description = "response of login")
public class RegisterResponse implements Serializable {

    private UserDto userDto;
    @Size(min = 1)
    @ApiObjectField(description = "session")
    private SessionInfo sessionInfo;

    public RegisterResponse(UserDto userDto, SessionInfo sessionInfo) {
        this.userDto = userDto;
        this.sessionInfo = sessionInfo;
    }

    public RegisterResponse() {

    }

    public RegisterResponse(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionIInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

}
