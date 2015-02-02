package com.lashgo.domain;

import com.lashgo.model.dto.RegisterInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 12.02.14.
 */
@Entity
@Table(name = "users")
public class Users implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_seq", sequenceName = "users_seq", allocationSize = 1)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "fio")
    private String fio;
    @Column(name = "about")
    private String about;
    @Column(name = "birth_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date birthDate;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "email")
    private String email;
    @Column(name = "is_admin")
    private boolean admin;
    @Column(name = "city")
    private String city;

    public Users() {
    }

    public Users(int id) {
        this.id = id;
    }

    public Users(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Users(RegisterInfo registerInfo) {
        this.login = registerInfo.getEmail();
        this.password = registerInfo.getPasswordHash();
        this.email = registerInfo.getEmail();
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
