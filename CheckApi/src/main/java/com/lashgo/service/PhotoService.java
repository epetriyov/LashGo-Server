package com.lashgo.service;

import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.PhotoPath;
import com.lashgo.model.dto.VoteAction;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Eugene on 28.04.2014.
 */
public interface PhotoService {

    PhotoPath savePhoto(String sessionId, int checkId, MultipartFile photo);

    void ratePhoto(String sessionId, VoteAction voteAction);

    CheckCounters getPhotoCounters(long photoId);

    Boolean likePhoto(Long photoid, String sessionId);

    PhotoDto getPhotoById(long photoId);

    void complainPhoto(String sessionId, long photoId);
}
