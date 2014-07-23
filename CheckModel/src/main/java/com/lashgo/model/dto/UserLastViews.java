package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 14.07.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "userLastViews")
public class UserLastViews implements Serializable{

    @ApiObjectField(description = "newsViewDate")
    private Date newsLastView;
    @ApiObjectField(description = "subscribesViewDate")
    private Date subscribesLastView;

    public UserLastViews() {
    }

    public UserLastViews(Date newsLastView, Date subscribesLastView) {

        this.newsLastView = newsLastView;
        this.subscribesLastView = subscribesLastView;
    }

    public Date getNewsLastView() {

        return newsLastView;
    }

    public void setNewsLastView(Date newsLastView) {
        this.newsLastView = newsLastView;
    }

    public Date getSubscribesLastView() {
        return subscribesLastView;
    }

    public void setSubscribesLastView(Date subscribesLastView) {
        this.subscribesLastView = subscribesLastView;
    }
}
