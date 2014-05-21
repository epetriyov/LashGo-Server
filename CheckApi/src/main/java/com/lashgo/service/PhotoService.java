package main.java.com.lashgo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Eugene on 28.04.2014.
 */
public interface PhotoService {

    void savePhoto(String sessionId, long checkId, MultipartFile photo);

    void ratePhoto(String sessionId, long photoId);
}
