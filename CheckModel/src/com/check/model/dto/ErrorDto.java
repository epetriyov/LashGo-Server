package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Eugene on 13.02.14.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ErrorDto implements Serializable {
    private String errorCode;

    public ErrorDto() {
    }

    public ErrorDto(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
