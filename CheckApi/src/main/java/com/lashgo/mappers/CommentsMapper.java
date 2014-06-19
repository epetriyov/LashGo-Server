package com.lashgo.mappers;

import com.lashgo.domain.Comments;
import com.lashgo.domain.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 21.04.2014.
 */
public class CommentsMapper implements RowMapper<Comments> {
    @Override
    public Comments mapRow(ResultSet resultSet, int i) throws SQLException {
        Comments comments = new Comments();
        comments.setId(resultSet.getLong("id"));
        comments.setContent(resultSet.getString("content"));
        comments.setCreateDate(resultSet.getTimestamp("create_date"));
        comments.setUser(new Users(resultSet.getInt("user_id")));
        return comments;
    }
}
