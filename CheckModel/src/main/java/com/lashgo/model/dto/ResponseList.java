package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Eugene on 22.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "responseList", description = "collection response")
public class ResponseList<T extends Serializable> extends ErrorResponse implements Serializable {
    private ArrayList<T> resultCollection;

    public ResponseList() {
    }

    public ResponseList(List<T> resultCollection) {
        this.resultCollection = new ArrayList<>(resultCollection);
    }

    public ResponseList(ErrorDto errorDto) {
        this.error = errorDto;
    }

    public ArrayList<T> getResultCollection() {
        return resultCollection;
    }

    public void setResultCollection(List<T> resultCollection) {
        this.resultCollection = new ArrayList<>(resultCollection);
    }

}

