package main.java.com.check.repository;

import main.java.com.check.domain.Photos;

/**
 * Created by Eugene on 28.04.2014.
 */

public interface PhotoDao {

    void savePhoto(Photos photos);

    boolean isPhotoExists(long userId, long checkId);

    void unrate(long photoId);

    void rate(long photoId);
}
