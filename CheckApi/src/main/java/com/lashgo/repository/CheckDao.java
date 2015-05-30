package com.lashgo.repository;

import com.lashgo.domain.Check;
import com.lashgo.model.CheckType;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
public interface CheckDao {

    List<CheckDto> getAllStartedChecks(int userId, String searchText, String checkType);

    CheckCounters getCheckCounters(int checkId);

    int getActiveChecksCount(CheckType selfie);

    List<Integer> getFinishedChecks();

    CheckDto getCheckById(int userdId, int checkId);

    Check getFinishedCheck();

    Check getCurrentCheck();

    boolean isCheckActive(int checkId);

    boolean isVoteGoing(int checkId);

    Check getVoteCheck();

    CheckDto getCheckById(int checkId);

    Number addNewCheck(CheckDto checkDto);

    boolean doesCheckNameExists(String checkName);
}
