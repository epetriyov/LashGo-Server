package com.lashgo.domain;

//import org.lightadmin.api.config.annotation.FileReference;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Eugene on 23.03.2014.
 */
@Entity
@Table(name = "checks")
public class Check {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "description")
    private String description;
    @Column(name = "task_photo")
//    @FileReference
    private String photo;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "vote_duration")
    private Integer voteDuration;

    public Check() {
    }

    public Check(Integer id)
    {
        this.id = id;
    }

    public Check(Integer id, String name, String photo, Date startDate) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getVoteDuration() {
        return voteDuration;
    }

    public void setVoteDuration(Integer voteDuration) {
        this.voteDuration = voteDuration;
    }
}
