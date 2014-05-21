package main.java.com.lashgo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Eugene on 23.03.2014.
 */
@Entity
@Table(name = "checks")
public class Check {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private int duration;
    @Column(name = "description")
    private String description;
    @Column(name = "photo")
    private String photo;
    @Column(name = "start_date")
    private Date startDate;

    public Check() {
    }

    public Check(long id)
    {
        this.id = id;
    }

    public Check(long id, String name, String photo, Date startDate) {
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

    public void setId(long id) {
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
}
