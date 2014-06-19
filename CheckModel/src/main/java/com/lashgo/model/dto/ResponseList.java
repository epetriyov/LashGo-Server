package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Eugene on 22.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "responseList", description = "collection response")
public class ResponseList<T extends Serializable> extends ErrorResponse implements Serializable {
    private List<T> resultCollection;

    public ResponseList() {
    }

    public ResponseList(List<T> resultCollection) {
        this.resultCollection = resultCollection;
    }

    public ResponseList(ErrorDto errorDto) {
        this.error = errorDto;
    }

    public Collection<T> getResultCollection() {
        return resultCollection;
    }

    public void setResultCollection(List<T> resultCollection) {
        this.resultCollection = resultCollection;
    }

}

