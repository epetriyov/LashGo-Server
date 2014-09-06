package com.lashgo.mappers;

import com.lashgo.model.dto.CheckDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 13.04.2014.
 */
public class CheckMapper implements org.springframework.jdbc.core.RowMapper<CheckDto> {
    @Override
    public CheckDto mapRow(ResultSet resultSet, int i) throws SQLException {
        CheckDto check = new CheckDto();
        check.setId(resultSet.getInt("check_id"));
        check.setName(resultSet.getString("check_name"));
        check.setDescription(resultSet.getString("check_description"));
        check.setStartDate(resultSet.getTimestamp("check_start_date"));
        check.setDuration(resultSet.getInt("check_duration"));
        check.setTaskPhotoUrl(resultSet.getString("check_task_photo"));
        check.setVoteDuration(resultSet.getInt("check_vote_duration"));
        check.setWinnerPhotoUrl(resultSet.getString("winner_photo"));
        check.setUserPhoto(resultSet.getString("user_photo"));
        UserDtoMapper usersMapper = new UserDtoMapper(false);
        check.setWinnerInfo(usersMapper.mapRow(resultSet, i));
        check.setPlayersCount(resultSet.getInt("players_count"));
        check.setLikesCount(resultSet.getInt("likes_count"));
        check.setCommentsCount(resultSet.getInt("comments_count"));
        return check;
    }
}
