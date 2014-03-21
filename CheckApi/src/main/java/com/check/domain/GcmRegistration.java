package main.java.com.check.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Eugene on 19.03.14.
 */
@Entity
@Table(name = "gcm_registration")
public class GcmRegistration {

    @Id
    @Column(name = "registration_id")
    private String registrationId;
    @Column(name = "uuid")
    private String uuid;

    public GcmRegistration() {

    }

    public GcmRegistration(String registrationId, String uuid) {
        this.registrationId = registrationId;
        this.uuid = uuid;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
