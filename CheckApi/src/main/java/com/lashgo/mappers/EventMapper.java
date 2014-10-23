package com.lashgo.mappers;

import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.EventDto;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.model.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 19.10.2014.
 */
public class EventMapper implements RowMapper<EventDto> {

    @Override
    public EventDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventDto eventDto = new EventDto();
        eventDto.setId(rs.getLong("id"));
        int userId1 = rs.getInt("uid1");
        if (userId1 > 0) {
            UserDto userDto = new UserDto();
            userDto.setId(userId1);
            userDto.setLogin(rs.getString("ulogin1"));
            userDto.setFio(rs.getString("ufio1"));
            userDto.setAvatar(rs.getString("uavatar1"));
            eventDto.setUser(userDto);
        }
        long photoId = rs.getLong("pid");
        if (photoId > 0) {
            PhotoDto photoDto = new PhotoDto();
            photoDto.setId(photoId);
            photoDto.setUrl(rs.getString("picture"));
            eventDto.setPhotoDto(photoDto);
        }
        int checkId = rs.getInt("cid");
        if (checkId > 0) {
            CheckDto checkDto = new CheckDto();
            checkDto.setId(checkId);
            checkDto.setName(rs.getString("cname"));
            checkDto.setTaskPhotoUrl(rs.getString("ctask_photo"));
            eventDto.setCheck(checkDto);
        }
        int userId2 = rs.getInt("uid2");
        if (userId2 > 0) {
            UserDto userDto = new UserDto();
            userDto.setId(userId2);
            userDto.setLogin(rs.getString("ulogin2"));
            userDto.setFio(rs.getString("ufio2"));
            userDto.setAvatar(rs.getString("uavatar2"));
            eventDto.setObjectUser(userDto);
        }
        eventDto.setAction(rs.getString("action"));
        eventDto.setEventDate(rs.getTimestamp("event_date"));
        return eventDto;
    }
}
