package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CheckDtoList implements Serializable {

    private List<CheckDto> checks;

    public CheckDtoList(List<CheckDto> checks) {
        this.checks = checks;
    }

    public CheckDtoList() {
    }

    public List<CheckDto> getChecks() {
        return checks;
    }

    public void setChecks(List<CheckDto> checks) {
        this.checks = checks;
    }
}
