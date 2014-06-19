package com.lashgo.repository;

import com.lashgo.model.dto.PhotoDto;
import com.lashgo.domain.Photos;

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
