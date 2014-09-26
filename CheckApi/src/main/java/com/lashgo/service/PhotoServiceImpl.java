package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Photos;
import com.lashgo.domain.Users;
import com.lashgo.error.PhotoReadException;
import com.lashgo.error.PhotoWriteException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.VoteAction;
import com.lashgo.repository.PhotoDao;
import com.lashgo.repository.PhotoLikesDao;
import com.lashgo.repository.UserShownPhotosDao;
import com.lashgo.repository.UserVotesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Eugene on 28.04.2014.
 */
@Service
@Transactional(readOnly = true)
public class PhotoServiceImpl implements PhotoService {

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

    private String buildNewPhotoName(int checkId, int userId) {
        StringBuilder photoNameBuilder = new StringBuilder("check_");
        photoNameBuilder.append(checkId);
        photoNameBuilder.append("_user_");
        photoNameBuilder.append(userId);
        photoNameBuilder.append(".jpg");
        return photoNameBuilder.toString();
    }

    @Transactional
    @Override
    public void savePhoto(String sessionId, int checkId, MultipartFile photo) {
        Users userDto = userService.getUserBySession(sessionId);
        if (!photoDao.isPhotoExists(userDto.getId(), checkId)) {

            if (!photo.isEmpty()) {
                BufferedImage src = null;
                try {
                    src = ImageIO.read(new ByteArrayInputStream(photo.getBytes()));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new PhotoReadException();
                }
                StringBuilder photoDestinationBuilder = new StringBuilder(CheckConstants.PHOTOS_FOLDER);
                String photoName = buildNewPhotoName(checkId, userDto.getId());
                String photoPath = photoDestinationBuilder.append(photoName).toString();
                File destination = new File(photoPath);
                try {
                    ImageIO.write(src, "jpg", destination);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new PhotoWriteException();
                }
                photoDao.savePhoto(new Photos(photoName, userDto.getId(), checkId));
            }
        } else {
            logger.error("Пользоваткль {} попытался отправить 2-е фото для задания {}",userDto.getId(),checkId);
            throw new ValidationException(ErrorCodes.PHOTO_ALREADY_EXISTS);
        }
    }

    @Transactional
    @Override
    public void ratePhoto(String sessionId, VoteAction voteAction) {
        Users users = userService.getUserBySession(sessionId);
        userVotesDao.addUserVote(users.getId(), voteAction.getVotedPhotoId());
        userShownPhotosDao.addUserShownPhotos(users.getId(), voteAction.getPhotoIds());
    }

    @Override
    public CheckCounters getPhotoCounters(long photoId) {
        return photoDao.getPhotoCounters(photoId);
    }

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
}
