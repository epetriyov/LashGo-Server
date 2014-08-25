package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Users;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.VotePhoto;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.PhotoDao;
import com.lashgo.repository.UserVotesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserService userService;

    @Autowired
    private UserVotesDao userVotesDao;

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

    @Override
    public List<VotePhoto> getVotePhotos(int checkId, String sessionId) {
        Users users = userService.getUserBySession(sessionId);
        return userVotesDao.getVotePhotos(users.getId(), checkId, CheckConstants.VOTE_PHOTOS_LIMIT);
    }

}
