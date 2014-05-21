package main.java.com.lashgo.repository;

import com.lashgo.model.dto.GcmRegistrationDto;

import java.util.List;

/**
 * Created by Eugene on 19.03.14.
 */
public interface GcmDao {

    void addRegistrationId(long userId, GcmRegistrationDto registrationDto);

    boolean isRegistrationIdExists(String registrationId);

    List<String> getAllRegistrationIds();
}
