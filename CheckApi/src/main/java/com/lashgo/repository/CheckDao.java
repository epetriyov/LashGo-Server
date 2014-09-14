package com.lashgo.repository;

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

    void addWinners();

    CheckDto getCheckById(int userdId, int checkId);
}
