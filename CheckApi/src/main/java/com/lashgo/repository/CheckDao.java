package com.lashgo.repository;

import com.lashgo.domain.Check;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;

import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
public interface CheckDao {

    CheckDto getNextCheck();

    List<CheckDto> getAllChecks(int userId);

    CheckCounters getCheckCounters(int checkId);

    int getActiveChecksCount();

    List<Integer> getVoteChecks();

    void addWinners(int checkId);

    CheckDto getCheckById(int userdId, int checkId);

    Check getCurrentCheck();
}
