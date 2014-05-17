package main.java.com.check.mappers;

import com.check.model.dto.CheckDto;
import com.check.model.dto.PhotoDto;
import com.check.model.dto.UserDto;
import main.java.com.check.domain.Check;
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
        photos.setRating(resultSet.getInt("rating"));
        if (mapType.equals(MapType.USER_JOIN)) {
            photos.setCheck(new CheckDto(resultSet.getInt("id"), resultSet.getString("name")));
        } else {
            photos.setUser(new UserDto(resultSet.getInt("id"), resultSet.getString("login"), resultSet.getString("avatar")));
        }
        return photos;
    }
}
