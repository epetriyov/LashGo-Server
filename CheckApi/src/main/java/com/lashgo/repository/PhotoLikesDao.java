package com.lashgo.repository;

/**
 * Created by Eugene on 15.09.2014.
 */
public interface PhotoLikesDao {
    void likeCheck(int userId, long photoId);

    void unlikeCheck(int userId, long photoId);

    boolean isUserLiked(int userId, long photoId);
}
