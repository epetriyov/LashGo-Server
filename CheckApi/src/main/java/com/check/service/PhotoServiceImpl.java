package main.java.com.check.service;

import com.check.model.dto.UserDto;
import main.java.com.check.CheckConstants;
import main.java.com.check.domain.Photos;
import main.java.com.check.repository.PhotoDao;
import main.java.com.check.repository.UserRatingDao;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.PhotoReadException;
import main.java.com.check.rest.error.PhotoWriteException;
import main.java.com.check.rest.error.ValidationException;
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
    private UserService userService;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private UserRatingDao userRatingDao;

    private final Logger logger = LoggerFactory.getLogger("FILE");

    @Transactional
    @Override
    public void savePhoto(String sessionId, long checkId, MultipartFile photo) {
        UserDto userDto = userService.getProfile(sessionId);
        if (!photoDao.isPhotoExists(userDto.getId(), checkId)) {
            StringBuilder photoDestinationBuilder = new StringBuilder(CheckConstants.PHOTOS_FOLDER);
            StringBuilder photoNameBuilder = new StringBuilder("check_");
            photoNameBuilder.append(checkId);
            photoNameBuilder.append("_user_");
            photoNameBuilder.append(userDto.getId());
            photoNameBuilder.append(".png");
            photoDestinationBuilder.append(photoNameBuilder);
            if (!photo.isEmpty()) {
                BufferedImage src = null;
                try {
                    src = ImageIO.read(new ByteArrayInputStream(photo.getBytes()));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new PhotoReadException(e);
                }
                File destination = new File(photoDestinationBuilder.toString());
                try {
                    ImageIO.write(src, "png", destination);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new PhotoWriteException(e);
                }
                photoDao.savePhoto(new Photos(photoNameBuilder.toString(), userDto.getId(), checkId));
            }
        } else {
            throw new ValidationException(ErrorCodes.PHOTO_ALREADY_EXISTS);
        }
    }

    @Transactional
    @Override
    public void ratePhoto(String sessionId, long photoId) {
        UserDto userDto = userService.getProfile(sessionId);
        if (userRatingDao.wasRated(userDto.getId(), photoId)) {
            photoDao.unrate(photoId);
            userRatingDao.removeRating(userDto.getId(), photoId);
        } else {
            photoDao.rate(photoId);
            userRatingDao.addRating(userDto.getId(), photoId);
        }
    }
}
