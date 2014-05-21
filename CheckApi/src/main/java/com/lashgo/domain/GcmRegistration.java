package main.java.com.lashgo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Eugene on 19.03.14.
 */
@Entity
@Table(name = "gcm_registrations")
public class GcmRegistration {

    @Id
    @Column(name = "registration_id")
    private String registrationId;
    @Column(name = "user_id")
    private String userId;

    public GcmRegistration() {

    }

    public GcmRegistration(String registrationId, String userId) {
        this.registrationId = registrationId;
        this.userId = userId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
