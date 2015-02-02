package com.lashgo.service;

import com.lashgo.domain.Users;
import com.lashgo.error.ValidationException;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.*;
import com.lashgo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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
    private UserDao userDao;

    @Autowired
    private CheckWinnersDao checkWinnersDao;

    @Autowired
    private CheckLikesDao userLikesDao;

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserShownPhotosDao userShownPhotosDao;

    @Override
    public List<CheckDto> getChecks(String sessionId, String searchText) {
        int userId = -1;
        if (sessionId != null) {
            userId = userService.getUserBySession(sessionId).getId();
        }
        return checkDao.getAllChecks(userId, searchText);
    }

    @Override
    public List<PhotoDto> getPhotos(int checkId) {
        return photoDao.getPhotosByCheckId(checkId);
    }

    @Override
    public List<VotePhoto> getVotePhotos(int checkId, String sessionId) {
        Users users = userService.getUserBySession(sessionId);
        return userShownPhotosDao.getUserShownPhotos(users.getId(), checkId);
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

    @Override
    public List<SubscriptionDto> getCheckUsers(String sessionId, int checkId) {
        int userId = -1;
        if (sessionId != null) {
            userId = userService.getUserBySession(sessionId).getId();
        }
        return userDao.getUsersByCheck(
                userId,
                checkId);
    }

    @Override
    public boolean isCheckActive(int checkId) {
        return checkDao.isCheckActive(checkId);
    }

    @Override
    public boolean isVoteGoing(int checkId) {
        return checkDao.isVoteGoing(checkId);
    }

    @Transactional
    private void addNextCheck(int checkId, int userId) {
        PhotoDto photo = photoDao.getPhoto(checkId, userId);
        CheckDto check = checkDao.getCheckById(checkId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(check.getStartDate());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        checkDao.addNextCheck(photo.getUrl(), calendar.getTime());
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void chooseWinner() {
        List<Integer> voteCheckIds = checkDao.getVoteChecks();
        if (voteCheckIds != null) {
            for (Integer id : voteCheckIds) {
                checkWinnersDao.addCheckWinner(id);
                logger.debug("Winner added");
                int userId = checkWinnersDao.getCheckWinner(id);
                if (userId > 0) {
                    eventDao.addWinEvent(id, userId);
                }
                addNextCheck(id, userId);
            }

        }
    }
}
