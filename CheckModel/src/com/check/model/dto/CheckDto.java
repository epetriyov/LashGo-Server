package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 14.04.2014.
 */
@ApiObject(name = "check")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CheckDto implements Serializable {

    @ApiObjectField(description = "id")
    private long id;

    private String name;

    private String description;

    private Date startDate;

    private int duration;

    private String photoUrl;

    public CheckDto() {

    }

    public CheckDto(long id, String name, String description, Date startDate, int duration, String photoUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
        this.photoUrl = photoUrl;
    }

    public CheckDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
