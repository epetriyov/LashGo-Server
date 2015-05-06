package com.lashgo.mappers;

import com.lashgo.model.dto.CheckDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 02.02.2015.
 */
public class CheckDtoMapper implements RowMapper<CheckDto> {
    @Override
    public CheckDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CheckDto check = new CheckDto();
        check.setId(rs.getInt("id"));
        check.setName(rs.getString("name"));
        check.setDescription(rs.getString("description"));
        check.setStartDate(rs.getTimestamp("start_date"));
        check.setDuration(rs.getInt("duration"));
        check.setTaskPhotoUrl(rs.getString("task_photo"));
        check.setVoteDuration(rs.getInt("vote_duration"));
        check.setCheckType(rs.getString("check_type"));
        return check;
    }
}
