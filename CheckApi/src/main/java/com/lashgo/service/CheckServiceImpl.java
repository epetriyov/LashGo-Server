package com.lashgo.service;

import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.domain.Check;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.PhotoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@Service
@Transactional(readOnly = true)
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private PhotoDao photoDao;

    @Override
    public List<CheckDto> getChecks() {
        List<Check> checkList = checkDao.getAllChecks();
        List<CheckDto> checkDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(checkList)) {
            for (Check check : checkList) {
                checkDtos.add(new CheckDto(check.getId(), check.getName(), check.getDescription(), check.getStartDate(), check.getDuration(), check.getPhoto(),check.getVoteDuration()));
            }
        }
        return checkDtos;
    }

    @Override
    public CheckDto getCurrentCheck() {
        Check check = checkDao.getLastCheck();
        if (check != null) {
            return new CheckDto(check.getId(), check.getName(), check.getDescription(), check.getStartDate(), check.getDuration(), check.getPhoto(), check.getVoteDuration());
        }
        return null;
    }

    @Override
    public List<PhotoDto> getPhotos(long checkId) {
        return photoDao.getPhotosByCheckId(checkId);
    }

}
