package com.lashgo.repository;

import com.lashgo.model.dto.NewsDto;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.07.2014.
 */
public interface NewsDao {

    List<NewsDto> getNews();

    int getNewerNews(Date lastViewDate);
}
