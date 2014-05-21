package main.java.com.lashgo.service;

import com.lashgo.model.dto.ContentDto;

import java.util.List;

/**
 * Created by Eugene on 06.05.2014.
 */
public interface ContentService {
    List<ContentDto> getNews(String sessionId);
}
