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
        return checkDao.getAllChecks();
    }

    @Override
    public CheckDto getCurrentCheck() {
        return checkDao.getLastCheck();
    }

    @Override
    public List<PhotoDto> getPhotos(long checkId) {
        return photoDao.getPhotosByCheckId(checkId);
    }

}
