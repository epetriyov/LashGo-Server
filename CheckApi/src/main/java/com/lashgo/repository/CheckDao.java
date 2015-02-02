package com.lashgo.repository;

import com.lashgo.domain.Check;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
public interface CheckDao {

    List<CheckDto> getAllChecks(int userId, String searchText);

    CheckCounters getCheckCounters(int checkId);

    int getActiveChecksCount();

    List<Integer> getVoteChecks();

    CheckDto getCheckById(int userdId, int checkId);

    Check getCurrentCheck();

    boolean isCheckActive(int checkId);

    boolean isVoteGoing(int checkId);

    Check getVoteCheck();

    void addNextCheck(String taskPhoto, Date startDate);

    CheckDto getCheckById(int checkId);
}
