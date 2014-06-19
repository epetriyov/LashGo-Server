package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 09.06.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "socialInfo", description = "access token")
public class SocialInfo implements Serializable {

    @Size(min = 1)
    @ApiObjectField(description = "access token")
    private String accessToken;
    @ApiObjectField(description = "access token secret (only for twitter and vk)")
    private String accessTokenSecret;
    @Size(min = 1)
    @ApiObjectField(description = "name of social network")
    private String socialName;

    public SocialInfo() {

    }

    public SocialInfo(String accessToken, String accessTokenSecret, String socialName) {
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        this.socialName = socialName;

    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}