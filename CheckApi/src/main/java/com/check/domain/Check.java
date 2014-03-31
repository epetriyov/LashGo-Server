package main.java.com.check.domain;

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
    @Column(name = "info")
    private String info;
    @Column(name = "expire_date")
    private Date expireDate;

    public Check() {
    }

    public Check(long id, String name, String info, Date expireDate) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.expireDate = expireDate;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
