package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 22.02.2015.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "ApnTokenDto", description = "token for APNS")
public class ApnTokenDto implements Serializable {

    @Size(min = 1)
    @ApiObjectField(description = "APNS token")
    private String token;

    public ApnTokenDto() {

    }

    public ApnTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

