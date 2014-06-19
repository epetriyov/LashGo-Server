package com.lashgo.repository;

/**
 * Created by Eugene on 28.04.2014.
 */
public interface UserRatingDao {
    boolean wasRated(int userId, long photoId);

    void removeRating(int userId, long photoId);

    void addRating(int userId, long photoId);
}
