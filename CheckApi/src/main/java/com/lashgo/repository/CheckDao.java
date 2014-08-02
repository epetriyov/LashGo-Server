package com.lashgo.repository;

import com.lashgo.model.dto.CheckDto;

import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
public interface CheckDao {

    CheckDto getNextCheck();

    List<CheckDto> getAllChecks();

    CheckDto getCheckById(long id);

    CheckDto getLastCheck();

    int getActiveChecksCount();
}
