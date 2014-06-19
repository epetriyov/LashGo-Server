package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;

/**
 * Created by Eugene on 10.06.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "sessionInfo", description = "access token")
public class ExtendedSocialInfo extends SocialInfo {

    @Size(min = 1)
    @ApiObjectField(description = "user's email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ExtendedSocialInfo() {

    }

    public ExtendedSocialInfo(String accessToken, String accessTokenSecret, String socialName, String email) {
        super(accessToken, accessTokenSecret, socialName);
        this.email = email;
    }

}
