package com.lashgo.mappers;

import com.lashgo.domain.News;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 14.07.2014.
 */
public class NewsMapper implements RowMapper<News> {
    @Override
    public News mapRow(ResultSet rs, int rowNum) throws SQLException {
        News news = new News();
        news.setContent(rs.getString("content"));
        news.setCreateDate(rs.getTimestamp("create_date"));
        news.setId(rs.getLong("id"));
        news.setImageUrl(rs.getString("image_url"));
        news.setTheme(rs.getString("theme"));
        return news;
    }
}
