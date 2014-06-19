package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.03.14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "gcmRegistrationId", description = "registrationId for GCM")
public class GcmRegistrationDto implements Serializable {

    @Size(min = 1)
    @ApiObjectField(description = "identifier of gcm registration")
    private String registrationId;

    public GcmRegistrationDto() {

    }

    public GcmRegistrationDto(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
