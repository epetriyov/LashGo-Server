package com.lashgo.repository;

import com.lashgo.model.dto.VotePhoto;

import java.util.List;

/**
 * Created by Eugene on 25.08.2014.
 */
public interface UserVotesDao {
    List<VotePhoto> getVotePhotos(int userId, int checkId, int limit);

    void addUserVotes(int userId, long[] photoIds);
}
