package main.java.com.check.service;

import com.check.model.dto.CheckDto;
import com.check.model.dto.CheckDtoList;
import com.check.model.dto.PhotoDtoList;

/**
 * Created by Eugene on 14.04.2014.
 */

public interface CheckService {
    CheckDtoList getChecks();

    CheckDto getCurrentCheck();

    PhotoDtoList getPhotos(long checkId);
}
