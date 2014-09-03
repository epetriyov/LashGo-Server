package com.lashgo.mappers;

import com.lashgo.model.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 01.09.2014.
 */
public class UserDtoMapper implements org.springframework.jdbc.core.RowMapper<UserDto> {

    private boolean extended;

    public UserDtoMapper(boolean extended) {
        this.extended = extended;
    }

    @Override
    public UserDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UserDto users = new UserDto();
        users.setId(resultSet.getInt("id"));
        users.setLogin(resultSet.getString("login"));
        users.setAvatar(resultSet.getString("avatar"));
        users.setFio(resultSet.getString("name"));
        users.setAbout(resultSet.getString("about"));
        users.setCity(resultSet.getString("city"));
        users.setBirthDate(resultSet.getTimestamp("birth_date"));
        users.setEmail(resultSet.getString("email"));
        if (extended) {
            users.setChecksCount(resultSet.getInt("checks_count"));
            users.setCommentsCount(resultSet.getInt("comments_count"));
            users.setLikesCount(resultSet.getInt("likes_count"));
            users.setUserSubscribers(resultSet.getInt("user_subscribers"));
            users.setUserSubscribes(resultSet.getInt("user_subscribes"));
        }
        return users;
    }
}
