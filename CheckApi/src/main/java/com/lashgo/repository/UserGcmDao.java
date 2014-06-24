package com.lashgo.repository;

/**
 * Created by Eugene on 23.06.2014.
 */
public interface UserGcmDao {
    void addUserRegistration(String registrationId, int userId);

    boolean isUserGcmExists(String registrationId, int userId);
}
