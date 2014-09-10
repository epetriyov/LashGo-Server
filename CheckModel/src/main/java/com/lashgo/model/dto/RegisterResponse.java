package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
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
    @ApiObjectField(description = "identifier of session")
    private String sessionId;

    public RegisterResponse(UserDto userDto, String sessionId) {
        this.userDto= userDto;
        this.sessionId = sessionId;
    }
    public RegisterResponse() {

    }

    public RegisterResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
