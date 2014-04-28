package main.java.com.check.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Eugene on 15.04.2014.
 */
@Entity
@Table(name = "photos")
public class Photos {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "picture")
    private String picture;

    @Column(name = "make_date")
    private Date makeDate;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", table = "users", referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "check_id", table = "checks", referencedColumnName = "id")
    private Check check;

    public Photos() {
    }

    public Photos(long id, String picture, Date makeDate, int rating, Users user, Check check) {

        this.id = id;
        this.picture = picture;
        this.makeDate = makeDate;
        this.rating = rating;
        this.user = user;
        this.check = check;
    }

    public Photos(String picture, int userId, long checkId) {
        this.picture = picture;
        this.user = new Users(userId);
        this.check = new Check(checkId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(Date makeDate) {
        this.makeDate = makeDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }
}
