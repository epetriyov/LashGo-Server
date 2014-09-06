package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Users;
import com.lashgo.error.ValidationException;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.VotePhoto;
import com.lashgo.model.dto.VotePhotosResult;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.CheckLikesDao;
import com.lashgo.repository.PhotoDao;
import com.lashgo.repository.UserShownPhotosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    private CheckLikesDao userLikesDao;

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserShownPhotosDao userShownPhotosDao;

    @Override
    public List<CheckDto> getChecks(String sessionId) {
        Users users = userService.getUserBySession(sessionId);
        return checkDao.getAllChecks(users.getId());
    }

    @Override
    public List<PhotoDto> getPhotos(long checkId) {
        return photoDao.getPhotosByCheckId(checkId);
    }

    @Override
    public VotePhotosResult getVotePhotos(int checkId, String sessionId, boolean isCountIncluded) {
        Users users = userService.getUserBySession(sessionId);
        List<VotePhoto> votePhotoList = userShownPhotosDao.getUserShownPhotos(users.getId(), checkId, CheckConstants.VOTE_PHOTOS_LIMIT);
        Integer photosCount = isCountIncluded ? userShownPhotosDao.getUserShownPhotosCount(users.getId(), checkId) : null;
        return new VotePhotosResult(votePhotoList, photosCount);
    }

    @Override
    @Transactional
    public boolean likeCheck(Integer checkId, String sessionId) {
        if (checkId == null) {
            throw new ValidationException(ErrorCodes.CHECK_ID_NULL);
        }
        Users users = userService.getUserBySession(sessionId);
        if (userLikesDao.isUserLiked(users.getId(), checkId)) {
            userLikesDao.unlikeCheck(users.getId(), checkId);
            return false;
        } else {
            userLikesDao.likeCheck(users.getId(), checkId);
            return true;
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void chooseWinner() {
        checkDao.addWinners();
    }

}
