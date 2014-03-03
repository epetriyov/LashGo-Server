package com.check.model.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 27.02.14
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class Response<T extends Serializable> implements Serializable {
    private T result;
    private ErrorDto error;

    public Response() {
    }

    public Response(T result) {
        this.result = result;
    }

    public Response(ErrorDto errorDto) {
        this.error = errorDto;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }
}
