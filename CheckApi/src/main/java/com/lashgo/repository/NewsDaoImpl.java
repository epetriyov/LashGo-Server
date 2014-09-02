package com.lashgo.repository;

import com.lashgo.mappers.NewsMapper;
import com.lashgo.model.dto.NewsDto;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<NewsDto> getNews() {
        return jdbcTemplate.query("SELECT n.* FROM news n ORDER BY n.create_date", new NewsMapper());
    }

    @Override
    public int getNewerNews(Date lastViewDate) {
        if (lastViewDate != null) {
            return jdbcTemplate.queryForObject("SELECT count(n.id) FROM news n WHERE n.create_date > ?", new Object[]{lastViewDate}, new int[]{Types.TIMESTAMP}, Integer.class);
        }
        return 0;
    }
}
