package main.java.com.check.core.domain;

import main.java.com.check.rest.dto.LoginInfo;

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
    @GeneratedValue(generator = "user_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_seq", allocationSize = 1)
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "about")
    private String about;

    @Column(name = "birth_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date birthDate;
    @Column(name = "avatar_name")
    private String avatarName;

    public Users() {
    }

    public Users(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public Users(LoginInfo loginInfo) {
        this.login = loginInfo.getLogin();
        this.passwordHash = loginInfo.getPasswordHash();
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
