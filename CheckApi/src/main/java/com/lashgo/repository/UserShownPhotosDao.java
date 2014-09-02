package com.lashgo.repository;

import com.lashgo.model.dto.VotePhoto;

import java.util.List;

/**
 * Created by Eugene on 31.08.2014.
 */
public interface UserShownPhotosDao {
    List<VotePhoto> getUserShownPhotos(int userId, int checkId, int limit);

    void addUserShownPhotos(int userId, long[] photoIds);

    int getUserShownPhotosCount(int userId, int checkId);
}
