
package com.lashgo.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

/**
 * Created by Eugene on 23.03.2014.
 */
public class Check {

    private int id;
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private int duration;

    @JacksonXmlProperty(isAttribute = true)
    private String description;

    @JacksonXmlProperty(isAttribute = true)
    private String photo;

    @JacksonXmlProperty(isAttribute = true)
    private Date startDate;

    @JacksonXmlProperty(isAttribute = true)
    private int voteDuration;

    public Check() {
    }

    public Check(int id) {
        this.id = id;
    }

    public Check(int id, String name, String photo, Date startDate) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getVoteDuration() {
        return voteDuration;
    }

    public void setVoteDuration(int voteDuration) {
        this.voteDuration = voteDuration;
    }
}
