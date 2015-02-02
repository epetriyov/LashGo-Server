package com.lashgo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Eugene on 14.07.2014.
 */
@Entity
@Table(name = "news")
public class News {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "theme")
    private String theme;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "image_url")
    private String imageUrl;

    public News() {

    }

    public News(long id, String theme, String content, Date createDate, String imageUrl) {
        this.id = id;
        this.theme = theme;
        this.content = content;
        this.createDate = createDate;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
