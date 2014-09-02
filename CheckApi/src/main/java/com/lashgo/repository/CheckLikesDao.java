package com.lashgo.repository;

/**
 * Created by Eugene on 01.09.2014.
 */
public interface CheckLikesDao {
    void likeCheck(int userId, int checkId);

    void unlikeCheck(int userId, int checkId);

    boolean isUserLiked(int userId, int checkId);
}
