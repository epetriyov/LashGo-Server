package com.check.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
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
