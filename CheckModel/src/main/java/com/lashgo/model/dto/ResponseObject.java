package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 27.02.14
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "responseObject", description = "object response")
public class ResponseObject<T extends Serializable> extends ErrorResponse implements Serializable {
    @ApiObjectField(description = "result object")
    private T result;

    public ResponseObject() {
    }

    public ResponseObject(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
