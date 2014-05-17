package main.java.com.check.repository;

import com.check.model.dto.PhotoDto;
import main.java.com.check.domain.Photos;

import java.util.List;

/**
 * Created by Eugene on 28.04.2014.
 */

public interface PhotoDao {

    void savePhoto(Photos photos);

    boolean isPhotoExists(long userId, long checkId);

    void unrate(long photoId);

    void rate(long photoId);

    List<PhotoDto> getPhotosByUserId(int userId);

    List<PhotoDto> getPhotosByCheckId(long checkId);
}
