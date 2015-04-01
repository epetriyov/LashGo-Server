package com.lashgo.mappers;

import com.lashgo.model.dto.EventDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 16.03.2015.
 */
public class EventDtoMapper implements RowMapper<EventDto> {

    @Override
    public EventDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventDto eventDto = new EventDto();
        eventDto.setId(rs.getLong("id"));
        eventDto.setAction(rs.getString("action"));
        eventDto.setEventDate(rs.getTimestamp("event_date"));
        return eventDto;
    }
}
