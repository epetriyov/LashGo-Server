package main.java.com.check.service;

import com.check.model.dto.ContentDtoList;

/**
 * Created by Eugene on 06.05.2014.
 */
public interface ContentService {
    ContentDtoList getNews(String sessionId);
}
