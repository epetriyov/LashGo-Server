package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Eugene on 05.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "photo")
public class PhotoDto implements Serializable {
    @Min(1)
    @ApiObjectField(description = "identifier")
    private long id;
    @NotEmpty
    @ApiObjectField(description = "url of photo")
    private String url;
    @Min(0)
    @ApiObjectField(description = "rating")
    private int rating;
    @NotNull
    @ApiObjectField(description = "check")
    private CheckDto check;
    @NotNull
    @ApiObjectField(description = "user")
    private UserDto user;

    public PhotoDto() {

    }

    public PhotoDto(long id, String url, CheckDto check, int rating, UserDto user) {
        this.id = id;
        this.url = url;
        this.check = check;
        this.rating = rating;
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public CheckDto getCheck() {
        return check;
    }

    public void setCheck(CheckDto check) {
        this.check = check;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
