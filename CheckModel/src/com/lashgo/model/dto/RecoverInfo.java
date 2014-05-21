package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

/**
 * Created by Eugene on 07.04.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "recoverInfo", description = "info for password recovery")
public class RecoverInfo implements Serializable {

    @NotEmpty
    @ApiObjectField(description = "login")
    private String login;

    public RecoverInfo() {
    }

    public RecoverInfo(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
