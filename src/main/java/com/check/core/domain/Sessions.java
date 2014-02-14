package main.java.com.check.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Eugene on 14.02.14.
 */
@Entity
@Table(name = "sessions")
public class Sessions {

    @Id
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "user_id")
    private int userId;

    public Sessions() {

    }

    public Sessions(int userId, String sessionId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
