package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

/**
 * Created by Eugene on 13.02.14.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "error")
public class ErrorDto implements Serializable {
    @NotEmpty
    @ApiObjectField(description = "code of error")
    private String errorCode;
    @NotEmpty
    @ApiObjectField(description = "message of error")
    private String errorMessage;

    public ErrorDto() {
    }

    public ErrorDto(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
