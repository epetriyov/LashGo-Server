package com.lashgo.repository;

import com.lashgo.domain.News;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.07.2014.
 */
public interface NewsDao {

    List<News> getNews();

    int getNewerNews(Date lastViewDate);
}
