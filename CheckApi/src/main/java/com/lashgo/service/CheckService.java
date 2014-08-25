package com.lashgo.service;

import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.VotePhoto;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */

public interface CheckService {
    List<CheckDto> getChecks();

    CheckDto getCurrentCheck();

    List<PhotoDto> getPhotos(long checkId);

    List<VotePhoto> getVotePhotos(int checkId,String sessionId);
}