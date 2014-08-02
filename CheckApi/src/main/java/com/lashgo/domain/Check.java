package com.lashgo.domain;

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
    @Column(name = "vote_duration")
    private int voteDuration;
    @Column(name = "shares_count")
    private int sharesCount;
    @Column(name = "likes_count")
    private int likesCount;
    @Column(name = "comments_count")
    private int commentsCount;
    @Column(name = "players_count")
    private int playersCount;

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

    public int getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(int sharesCount) {
        this.sharesCount = sharesCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
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

    public int getVoteDuration() {
        return voteDuration;
    }

    public void setVoteDuration(int voteDuration) {
        this.voteDuration = voteDuration;
    }
}
