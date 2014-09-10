package com.lashgo.service;

import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.VotePhotosResult;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */

public interface CheckService {
    List<CheckDto> getChecks(String sessionId);

    List<PhotoDto> getPhotos(int checkId);

    VotePhotosResult getVotePhotos(int checkId, String sessionId, boolean isCountIncluded);

    boolean likeCheck(Integer checkId, String sessionId);

    CheckDto getCheckById(String sessionId, int checkId);
}
