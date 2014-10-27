package com.lashgo.repository;

import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.domain.Photos;

import java.util.List;

/**
 * Created by Eugene on 28.04.2014.
 */

public interface PhotoDao {

    void savePhoto(Photos photos);

    boolean isPhotoExists(int userId, int checkId);

    CheckCounters getPhotoCounters(long photoId);

    List<PhotoDto> getPhotosByUserId(int userId);

    List<PhotoDto> getPhotosByCheckId(int checkId);

    PhotoDto getPhotoById(long photoId);
}
