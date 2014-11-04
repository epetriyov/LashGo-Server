package com.lashgo.repository;

/**
 * Created by Eugene on 30.10.2014.
 */
public interface UserComplainDao {

    void makeComplain(int userId, long photoId);

    boolean isComplainExists(int userId, long photoId);
}
