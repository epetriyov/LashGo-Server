package com.lashgo.mappers;

import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.UserDto;

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
        check.setPlayersCount(resultSet.getInt("players_count"));
        String userPhoto = resultSet.getString("user_photo");
        if (userPhoto != null) {
            PhotoDto userPhotoDto = new PhotoDto();
            userPhotoDto.setUrl(userPhoto);
            check.setUserPhotoDto(userPhotoDto);
        }
        int winnerId = resultSet.getInt("winner_id");
        if (winnerId > 0) {
            UserDtoMapper usersMapper = new UserDtoMapper(false);
            UserDto userDto = usersMapper.mapRow(resultSet, i);
            userDto.setId(winnerId);
            check.setWinnerInfo(userDto);
            PhotoDto winnerPhotoDto = new PhotoDto();
            winnerPhotoDto.setId(resultSet.getLong("winner_photo_id"));
            winnerPhotoDto.setCommentsCount(resultSet.getInt("comments_count"));
            winnerPhotoDto.setUrl(resultSet.getString("winner_photo"));
            check.setWinnerPhotoDto(winnerPhotoDto);
        }
        return check;
    }
}
