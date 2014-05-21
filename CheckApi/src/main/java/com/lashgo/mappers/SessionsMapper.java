package main.java.com.lashgo.mappers;

import main.java.com.lashgo.domain.Sessions;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 13.04.2014.
 */
public class SessionsMapper implements org.springframework.jdbc.core.RowMapper<main.java.com.lashgo.domain.Sessions> {
    @Override
    public Sessions mapRow(ResultSet resultSet, int i) throws SQLException {
        Sessions sessions = new Sessions();
        sessions.setSessionId(resultSet.getString("session_id"));
        sessions.setUserId(resultSet.getInt("user_id"));
        sessions.setStartTime(resultSet.getTimestamp("start_time"));
        return sessions;
    }
}
