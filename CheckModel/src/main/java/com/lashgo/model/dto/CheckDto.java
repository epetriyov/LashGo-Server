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
 * Created by Eugene on 14.04.2014.
 */
@ApiObject(name = "check", description = "Task for make a photo")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CheckDto implements Serializable {
    @Min(1)
    @ApiObjectField(description = "identifier")
    private long id;

    @ApiObjectField(description = "name")
    private String name;
    @Size(min = 1)
    @ApiObjectField(description = "description")
    private String description;
    @NotNull
    @ApiObjectField(description = "start date")
    private Date startDate;
    @Min(1)
    @ApiObjectField(description = "duration")
    private int duration;
    @ApiObjectField(description = "task photo url")
    private String taskPhotoUrl;
    @ApiObjectField(description = "photo url")
    private String photoUrl;
    @Min(1)
    @ApiObjectField(description = "vote duration")
    private int voteDuration;
    @Min(0)
    @ApiObjectField(description = "count of shares")
    private int sharesCount;
    @Min(0)
    @ApiObjectField(description = "count of likes")
    private int likesCount;
    @Min(0)
    @ApiObjectField(description = "count of comments")
    private int commentsCount;
    @Min(0)
    @ApiObjectField(description = "count of players")
    private int playersCount;
    public CheckDto() {

    }

    public CheckDto(long id, String name, String description, Date startDate, int duration, String photoUrl, int voteDuration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
        this.photoUrl = photoUrl;
        this.voteDuration = voteDuration;
    }

    public CheckDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getTaskPhotoUrl() {
        return taskPhotoUrl;
    }

    public void setTaskPhotoUrl(String taskPhotoUrl) {
        this.taskPhotoUrl = taskPhotoUrl;
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

    public int getVoteDuration() {
        return voteDuration;
    }

    public void setVoteDuration(int voteDuration) {
        this.voteDuration = voteDuration;
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
