package main.java.com.check.repository;

import main.java.com.check.domain.Check;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
public interface CheckDao {

    Check getNextCheck();

    List<Check> getAllChecks();
}
