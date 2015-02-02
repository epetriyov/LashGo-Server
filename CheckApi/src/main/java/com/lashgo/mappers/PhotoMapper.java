package com.lashgo.mappers;

import com.lashgo.model.dto.PhotoDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 02.02.2015.
 */
public class PhotoMapper implements RowMapper<PhotoDto> {
    @Override
    public PhotoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        PhotoDto photos = new PhotoDto();
        photos.setId(rs.getInt("id"));
        photos.setUrl(rs.getString("picture"));
        return photos;
    }
}
