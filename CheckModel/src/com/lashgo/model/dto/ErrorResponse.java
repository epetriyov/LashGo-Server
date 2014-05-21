package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

/**
 * Created by Eugene on 22.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "error response", description = "error response")
public class ErrorResponse implements Serializable {
    @ApiObjectField(description = "error info")
    protected ErrorDto error;


    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }
}
