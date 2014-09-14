package com.lashgo.mappers;

import com.lashgo.model.dto.CommentDto;
import com.lashgo.model.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 21.04.2014.
 */
public class CommentsMapper implements RowMapper<CommentDto> {

    @Override
    public CommentDto mapRow(ResultSet resultSet, int i) throws SQLException {
        CommentDto comments = new CommentDto();
        comments.setId(resultSet.getLong("id"));
        comments.setContent(resultSet.getString("content"));
        comments.setCreateDate(resultSet.getTimestamp("create_date"));
        comments.setUser(new UserDto(resultSet.getInt("user_id"), resultSet.getString("login"), resultSet.getString("fio"),resultSet.getString("avatar")));
        return comments;
    }
}
