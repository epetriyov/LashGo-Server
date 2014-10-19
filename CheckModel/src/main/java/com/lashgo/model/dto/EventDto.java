package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 19.10.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "events", description = "event")
public class EventDto implements Serializable {

    @Min(1)
    @ApiObjectField(description = "id of event")
    private long id;

    @ApiObjectField(description = "check")
    private CheckDto check;
    @ApiObjectField(description = "user")
    private UserDto user;

    @Size(min = 1)
    @ApiObjectField(description = "action's code  of event")
    private String action;

    @NotNull
    @ApiObjectField(description = "event date")
    private Date eventDate;

    @ApiObjectField(description = "photo info")
    private PhotoDto photoDto;

    @ApiObjectField(description = "object user")
    private UserDto objectUser;

    public EventDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CheckDto getCheck() {
        return check;
    }

    public void setCheck(CheckDto check) {
        this.check = check;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public PhotoDto getPhotoDto() {
        return photoDto;
    }

    public void setPhotoDto(PhotoDto photoDto) {
        this.photoDto = photoDto;
    }

    public UserDto getObjectUser() {
        return objectUser;
    }

    public void setObjectUser(UserDto objectUser) {
        this.objectUser = objectUser;
    }
}
