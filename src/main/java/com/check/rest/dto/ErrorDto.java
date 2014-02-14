package main.java.com.check.rest.dto;

import java.io.Serializable;

/**
 * Created by Eugene on 13.02.14.
 */
public class ErrorDto implements Serializable {
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorCode;

    public ErrorDto(String errorCode) {
        this.errorCode = errorCode;
    }


}
