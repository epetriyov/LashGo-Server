package main.java.com.check.repository;

import com.check.model.dto.GcmRegistrationDto;

import java.util.List;

/**
 * Created by Eugene on 19.03.14.
 */
public interface GcmDao {

    void addRegistrationId(String uuid, GcmRegistrationDto registrationDto);

    boolean isRegistrationIdExists(String registrationId);

    List<String> getAllRegistrationIds();
}
