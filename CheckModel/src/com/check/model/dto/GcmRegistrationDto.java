package com.check.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.03.14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class GcmRegistrationDto implements Serializable {

    @NotEmpty
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
