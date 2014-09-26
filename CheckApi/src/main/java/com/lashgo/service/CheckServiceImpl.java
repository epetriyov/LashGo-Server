package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Users;
import com.lashgo.error.ValidationException;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.VotePhotosResult;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.CheckLikesDao;
import com.lashgo.repository.PhotoDao;
import com.lashgo.repository.UserShownPhotosDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@Service
@Transactional(readOnly = true)
@EnableScheduling
public class CheckServiceImpl implements CheckService {

    private final Logger logger = LoggerFactory.getLogger("FILE");

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
        int userId = -1;
        if (sessionId != null) {
            userId = userService.getUserBySession(sessionId).getId();
        }
        return checkDao.getAllChecks(userId);
    }

    @Override
    public List<PhotoDto> getPhotos(int checkId) {
        return photoDao.getPhotosByCheckId(checkId);
    }

    @Override
    public VotePhotosResult getVotePhotos(int checkId, String sessionId, boolean isCountIncluded) {
        Users users = userService.getUserBySession(sessionId);
        List<PhotoDto> votePhotoList = userShownPhotosDao.getUserShownPhotos(users.getId(), checkId, CheckConstants.VOTE_PHOTOS_LIMIT);
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

    @Override
    public CheckDto getCheckById(String sessionId, int checkId) {
        int userId = -1;
        if (sessionId != null) {
            userId = userService.getUserBySession(sessionId).getId();
        }
        return checkDao.getCheckById(userId, checkId);
    }

    @Override
    public CheckCounters getCheckCounters(int checkId) {
        return checkDao.getCheckCounters(checkId);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void chooseWinner() {
        logger.debug("Winner choosing");
        List<Integer> voteCheckIds = checkDao.getVoteChecks();
        if (voteCheckIds != null) {
            for (Integer id : voteCheckIds) {
                checkDao.addWinners(id);
            }

        }
    }
}
