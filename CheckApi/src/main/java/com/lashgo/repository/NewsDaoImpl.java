package com.lashgo.repository;

import com.lashgo.domain.News;
import com.lashgo.mappers.NewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.07.2014.
 */
@Repository
public class NewsDaoImpl implements NewsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<News> getNews() {
        return jdbcTemplate.query("SELECT n.* FROM news n ORDER BY create_date", new NewsMapper());
    }

    @Override
    public int getNewerNews(Date lastViewDate) {
        if (lastViewDate != null) {
            return jdbcTemplate.queryForObject("SELECT count(n.id) FROM news WHERE create_date > ?", new Object[]{lastViewDate}, new int[]{Types.TIMESTAMP}, Integer.class);
        }
        return 0;
    }
}
