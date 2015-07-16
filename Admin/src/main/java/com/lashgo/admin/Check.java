package com.lashgo.admin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 17.06.2015.
 */
@Entity
@Table(name = "checks")
public class Check implements Serializable {


    @javax.persistence.Id
    @SequenceGenerator(name="checks_id_seq",
            sequenceName="checks_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="checks_id_seq")
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "task_photo")
    private String photo;
    @Column(name = "vote_duration")
    private Integer voteDuration;

    public Check() {
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getVoteDuration() {
        return voteDuration;
    }

    public void setVoteDuration(Integer voteDuration) {
        this.voteDuration = voteDuration;
    }

    public int getId() {

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
}
