package com.lashgo.service;

import com.lashgo.model.dto.NewsDto;
import com.lashgo.repository.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Eugene on 06.05.2014.
 */
@Transactional(readOnly = true)
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public List<NewsDto> getNews() {
        return newsDao.getNews();
    }
}
