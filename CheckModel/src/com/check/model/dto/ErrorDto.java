package com.check.model.dto;

import java.io.Serializable;

/**
 * Created by Eugene on 13.02.14.
 */
public class ErrorDto implements Serializable {
    private String errorCode;

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
