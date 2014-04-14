package main.java.com.check.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Eugene on 14.04.2014.
 */
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", table = "users", referencedColumnName = "id")
    private Users user;
    @Column(name = "create_date")
    private Date createDate;

    public Comments() {
    }

    public Comments(long id, String content, Users user, Date createDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
