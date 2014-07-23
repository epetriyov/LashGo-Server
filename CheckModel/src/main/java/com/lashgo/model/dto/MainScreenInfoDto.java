package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 09.07.2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiObject(name = "mainScreenInfo", description = "info for main screen")
public class MainScreenInfoDto implements Serializable {
    @Size(min = 1)
    @ApiObjectField(description = "userName")
    private String userName;
    @Size(min = 1)
    @ApiObjectField(description = "userAvatar")
    private String userAvatar;
    @ApiObjectField(description = "tasksCount")
    private int tasksCount;
    @ApiObjectField(description = "newsCount")
    private int newsCount;
    @ApiObjectField(description = "subscribesCount")
    private int subscribesCount;

    public MainScreenInfoDto(String userName, String userAvatar, int tasksCount, int newsCount, int subscribesCount) {
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.tasksCount = tasksCount;
        this.newsCount = newsCount;
        this.subscribesCount = subscribesCount;
    }

    public MainScreenInfoDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public int getSubscribesCount() {
        return subscribesCount;
    }

    public void setSubscribesCount(int subscribesCount) {
        this.subscribesCount = subscribesCount;
    }
}
