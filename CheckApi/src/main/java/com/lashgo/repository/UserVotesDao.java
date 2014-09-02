package com.lashgo.repository;

/**
 * Created by Eugene on 25.08.2014.
 */
public interface UserVotesDao {

    void addUserVote(int userId, long photoIds);
}
