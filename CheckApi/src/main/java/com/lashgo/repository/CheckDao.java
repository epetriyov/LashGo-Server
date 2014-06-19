package com.lashgo.repository;

import com.lashgo.domain.Check;

import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
public interface CheckDao {

    Check getNextCheck();

    List<Check> getAllChecks();

    Check getCheckById(long id);

    Check getLastCheck();
}
