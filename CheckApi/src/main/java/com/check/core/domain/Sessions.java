package main.java.com.check.core.domain;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "start_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "uuid")
    private String uuid;

    public Sessions() {

    }

    public Sessions(int userId, String sessionId, Date startTime, String uuid) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.startTime = startTime;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
