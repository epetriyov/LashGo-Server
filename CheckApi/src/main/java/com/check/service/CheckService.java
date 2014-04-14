package main.java.com.check.service;

import com.check.model.dto.CheckDto;
import com.check.model.dto.CheckDtoList;

/**
 * Created by Eugene on 14.04.2014.
 */

public interface CheckService {
    CheckDtoList getChecks();

    CheckDto getCurrentCheck();
}
