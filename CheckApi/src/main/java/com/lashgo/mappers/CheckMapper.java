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
        check.setVoteDuration(resultSet.getInt("vote_duration"));
        check.setWinnerPhotoUrl(resultSet.getString("winner_photo"));
        UserDtoMapper usersMapper = new UserDtoMapper();
        check.setWinnerInfo(usersMapper.mapRow(resultSet, i));
        return check;
    }
}
