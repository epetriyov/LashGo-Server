package com.lashgo.service;

import com.lashgo.model.dto.*;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */

public interface CheckService {
    List<CheckDto> getChecks(String sessionId, String searchText);

    List<PhotoDto> getPhotos(int checkId);

    List<VotePhoto> getVotePhotos(int checkId, String sessionId);

    boolean likeCheck(Integer checkId, String sessionId);

    CheckDto getCheckById(String sessionId, int checkId);

    CheckCounters getCheckCounters(int checkId);

    List<SubscriptionDto> getCheckUsers(int checkId);
}
