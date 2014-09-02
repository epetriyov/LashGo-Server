package com.lashgo.mappers;

import com.lashgo.model.dto.NewsDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 14.07.2014.
 */
public class NewsMapper implements RowMapper<NewsDto> {
    @Override
    public NewsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        NewsDto news = new NewsDto();
        news.setContent(rs.getString("content"));
        news.setCreateDate(rs.getTimestamp("create_date"));
        news.setId(rs.getInt("id"));
        news.setImageUrl(rs.getString("image_url"));
        news.setTheme(rs.getString("theme"));
        return news;
    }
}
