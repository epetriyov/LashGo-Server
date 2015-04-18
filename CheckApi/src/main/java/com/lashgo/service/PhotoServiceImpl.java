package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Photos;
import com.lashgo.domain.Users;
import com.lashgo.error.PhotoReadException;
import com.lashgo.error.PhotoWriteException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.*;
import com.lashgo.repository.*;
import com.lashgo.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eugene on 28.04.2014.
 */
@Service
@Transactional(readOnly = true)
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private UserComplainDao userComplainDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private CheckService checkService;

    @Autowired
    private PhotoLikesDao userLikesDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserShownPhotosDao userShownPhotosDao;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private UserVotesDao userVotesDao;

    private final Logger logger = LoggerFactory.getLogger("FILE");

    public void savePhoto(String photoName, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            InputStream inputStream;
            try {
                inputStream = photo.getInputStream();
                savePhoto(photoName, inputStream);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new PhotoReadException();
            }

        } else {
            throw new ValidationException(ErrorCodes.EMPTY_PHOTO);
        }
    }

    @Override
    public void savePhoto(String photoName, InputStream inputStream) {
        if (inputStream != null) {
            BufferedImage src;
            try {
                src = ImageIO.read(inputStream);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new PhotoReadException();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new PhotoReadException();
                }
            }
            StringBuilder photoDestinationBuilder = new StringBuilder(CheckConstants.PHOTOS_FOLDER);
            String photoPath = photoDestinationBuilder.append(photoName).toString();
            File destination = new File(photoPath);
            try {
                ImageIO.write(src, "jpg", destination);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new PhotoWriteException();
            }
        } else {
            logger.error("Image { } is empty", photoName);
            throw new PhotoReadException();
        }
    }

    @Transactional
    @Override
    public PhotoPath savePhoto(String sessionId, int checkId, MultipartFile photo) {
        Users userDto = userService.getUserBySession(sessionId);
        if (photoDao.isPhotoExists(userDto.getId(), checkId)) {
            throw new ValidationException(ErrorCodes.PHOTO_ALREADY_EXISTS);
        }
        if (!checkService.isCheckActive(checkId)) {
            throw new ValidationException(ErrorCodes.CHECK_IS_NOT_ACTIVE);
        }
        String photoName = CheckUtils.buildNewPhotoName(checkId, userDto.getId());
        savePhoto(photoName, photo);
        photoDao.savePhoto(new Photos(photoName, userDto.getId(), checkId));
        eventDao.addCheckParticipateEvent(userDto.getId(), checkId);
        return new PhotoPath(photoName);
    }

    @Transactional
    @Override
    public void ratePhoto(String sessionId, VoteAction voteAction) {
        Users users = userService.getUserBySession(sessionId);
        if (!checkService.isVoteGoing(voteAction.getCheckId())) {
            throw new ValidationException(ErrorCodes.CHECK_IS_NOT_ACTIVE);
        }
        userVotesDao.addUserVote(users.getId(), voteAction.getVotedPhotoId());
        eventDao.addVoteEvent(users.getId(), voteAction.getVotedPhotoId());
        userShownPhotosDao.addUserShownPhotos(users.getId(), voteAction.getPhotoIds());
    }

    @Override
    public CheckCounters getPhotoCounters(long photoId) {
        return photoDao.getPhotoCounters(photoId);
    }

    @Transactional
    @Override
    public Boolean likePhoto(Long photoId, String sessionId) {
        if (photoId == null) {
            throw new ValidationException(ErrorCodes.PHOTO_ID_NULL);
        }
        Users users = userService.getUserBySession(sessionId);
        if (userLikesDao.isUserLiked(users.getId(), photoId)) {
            userLikesDao.unlikeCheck(users.getId(), photoId);
            return false;
        } else {
            userLikesDao.likeCheck(users.getId(), photoId);
            return true;
        }
    }

    @Override
    public PhotoDto getPhotoById(long photoId) {
        return photoDao.getPhotoById(photoId);
    }

    @Transactional
    @Override
    public void complainPhoto(String sessionId, long photoId) {
        Users users = userService.getUserBySession(sessionId);
        if (!userComplainDao.isComplainExists(users.getId(), photoId)) {
            userComplainDao.makeComplain(users.getId(), photoId);
        }
    }

    @Transactional
    @Override
    public Boolean likePhoto(LikedPhotoDto photoDto, String sessionId) {
        return likePhoto(photoDto.getPhotoId(), sessionId);
    }
}
