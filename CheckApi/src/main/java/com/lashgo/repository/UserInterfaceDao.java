package com.lashgo.repository;

/**
 * Created by Eugene on 28.04.2014.
 */
public interface UserInterfaceDao {
    boolean isUserInterfaceExists(int userId, int interfaceId);

    void addUserInteface(int userId, int interfaceId);
}
