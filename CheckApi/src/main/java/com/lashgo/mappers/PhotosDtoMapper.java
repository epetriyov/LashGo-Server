package com.lashgo.mappers;

import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 05.05.2014.
 */
public class PhotosDtoMapper implements RowMapper<PhotoDto> {

    public static enum MapType {CHECK_JOIN, USER_JOIN}

    private MapType mapType;

    public PhotosDtoMapper(MapType mapType) {
        super();
        this.mapType = mapType;
    }

    @Override
    public PhotoDto mapRow(ResultSet resultSet, int position) throws SQLException {
        PhotoDto photos = new PhotoDto();
        photos.setId(resultSet.getInt("id_photo"));
        photos.setUrl(resultSet.getString("picture"));
        photos.setCommentsCount(resultSet.getInt("comments_count"));
        photos.setLikesCount(resultSet.getInt("likes_count"));
        if (MapType.USER_JOIN.equals(mapType)) {
            photos.setBanned(resultSet.getInt("is_banned") == 1);
            photos.setWinner(resultSet.getInt("is_winner") == 1);
            photos.setCheck(new CheckDto(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("task_photo")));
        } else if (MapType.CHECK_JOIN.equals(mapType)) {
            photos.setUser(new UserDto(resultSet.getInt("id"), resultSet.getString("login"), resultSet.getString("fio"), resultSet.getString("avatar")));
        }
        return photos;
    }
}
