package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@ApiObject(name = "list of checks")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CheckDtoList implements Serializable {

    @ApiObjectField(description = "list of checks")
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
