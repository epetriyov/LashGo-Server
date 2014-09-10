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
        String userPhoto = resultSet.getString("user_photo");
        if (userPhoto != null) {
            PhotoDto userPhotoDto = new PhotoDto();
            userPhotoDto.setUrl(userPhoto);
            userPhotoDto.setLikesCount(resultSet.getInt("u_p_likes_count"));
            userPhotoDto.setCommentsCount(resultSet.getInt("u_p_comments_count"));
            check.setUserPhotoDto(userPhotoDto);
        }
        int winnerId = resultSet.getInt("winner_id");
        if (winnerId > 0) {
            UserDtoMapper usersMapper = new UserDtoMapper(false);
            UserDto userDto = usersMapper.mapRow(resultSet, i);
            userDto.setId(winnerId);
            check.setWinnerInfo(userDto);
            PhotoDto winnerPhotoDto = new PhotoDto();
            winnerPhotoDto.setUrl(resultSet.getString("winner_photo"));
            winnerPhotoDto.setLikesCount(resultSet.getInt("w_p_likes_count"));
            winnerPhotoDto.setCommentsCount(resultSet.getInt("w_p_comments_count"));
            check.setWinnerPhotoDto(winnerPhotoDto);
        }
        check.setPlayersCount(resultSet.getInt("players_count"));
        check.setLikesCount(resultSet.getInt("likes_count"));
        check.setCommentsCount(resultSet.getInt("comments_count"));
        return check;
    }
}
