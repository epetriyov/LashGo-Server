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
 * Created by Eugene on 30.08.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "news", description = "news")
public class NewsDto implements Serializable {

    @Min(1)
    @ApiObjectField(description = "id of news")
    private int id;

    @Size(min = 1)
    @ApiObjectField(description = "news header")
    private String theme;

    @Size(min = 1)
    @ApiObjectField(description = "content text")
    private String content;
    @NotNull
    @ApiObjectField(description = "create date")
    private Date createDate;

    @ApiObjectField(description = "url of news image")
    private String imageUrl;

    public NewsDto() {
    }

    public NewsDto(int id, String theme, String content, Date createDate, String imageUrl) {

        this.id = id;
        this.theme = theme;
        this.content = content;
        this.createDate = createDate;
        this.imageUrl = imageUrl;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
