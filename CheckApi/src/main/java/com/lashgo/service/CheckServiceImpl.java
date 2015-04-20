package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Users;
import com.lashgo.error.ValidationException;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.*;
import com.lashgo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<CheckDto> getChecks(String sessionId, String searchText, String checkType) {
        int userId = -1;
        if (sessionId != null) {
            userId = userService.getUserBySession(sessionId).getId();
        }
        return checkDao.getAllStartedChecks(userId, searchText,checkType);
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
    @Override
    public void createNewCheck(CheckDto checkDto) {
        if (!checkDao.doesCheckNameExists(checkDto.getName())) {
            checkDao.addNewCheck(checkDto);
        } else {
            throw new ValidationException(ErrorCodes.CHECK_NAME_EXISTS);
        }
    }

    @Transactional
    private void addNextCheck(int checkId, int userId) {
        PhotoDto photo = photoDao.getPhoto(checkId, userId);
        CheckDto check = checkDao.getCheckById(checkId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(check.getStartDate());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        checkDao.addNewCheck(new CheckDto(messageSource.getMessage("check.name", null, Locale.US), messageSource.getMessage("check.description", null, Locale.US), calendar.getTime(), CheckConstants.DURATION, CheckConstants.VOTE_DURATTON, photo.getUrl()));
    }

    @Scheduled(cron = "1 * * * * *")
    @Transactional
    public void chooseWinner() {
        List<Integer> voteCheckIds = checkDao.getFinishedChecks();
        if (voteCheckIds != null) {
            for (Integer id : voteCheckIds) {
                checkWinnersDao.addCheckWinner(id);
                int userId = checkWinnersDao.getCheckWinner(id);
                logger.debug("Winner added {}", userId);
                if (userId > 0) {
                    eventDao.addWinEvent(id, userId);
                }
                CheckDto checkDto = checkDao.getCheckById(id);
                if (checkDto.getName().equals(messageSource.getMessage("check.name", null, Locale.US))) {
                    addNextCheck(id, userId);
                }
            }
        }
    }
}
