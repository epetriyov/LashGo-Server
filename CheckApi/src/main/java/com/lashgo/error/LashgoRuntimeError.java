package com.lashgo.error;

/**
 * Created by Eugene on 22.06.2014.
 */
public class LashgoRuntimeError extends RuntimeException {
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LashgoRuntimeError(String errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
