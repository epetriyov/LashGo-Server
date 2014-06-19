package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 07.04.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "recoverInfo", description = "info for password recovery")
public class RecoverInfo implements Serializable {

    @Size(min = 1)
    @ApiObjectField(description = "email")
    private String email;

    public RecoverInfo() {
    }

    public RecoverInfo(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
