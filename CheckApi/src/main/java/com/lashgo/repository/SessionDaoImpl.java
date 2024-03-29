package com.lashgo.repository;

import com.lashgo.domain.Sessions;
import com.lashgo.mappers.SessionsMapper;
import com.lashgo.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Eugene on 14.02.14.
 */
@Repository
public class SessionDaoImpl implements SessionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Sessions createSession(final int userId) {
        Sessions sessions = getSessionByUser(userId);
        String sessionId = generateSession(userId);
        if (sessions == null) {
            jdbcTemplate.update("INSERT INTO sessions (session_id, user_id,start_time) VALUES (?,?,current_timestamp)", sessionId, userId);
        } else {
            jdbcTemplate.update("UPDATE sessions SET session_id = ?, start_time = current_timestamp WHERE user_id = ?", sessionId, userId);
        }
        return jdbcTemplate.queryForObject("SELECT s.* FROM sessions s WHERE s.session_id = ?", new SessionsMapper(), sessionId);
    }


    private String generateSession(int userId) {
        return CheckUtils.md5(String.valueOf(userId) + new Date().getTime());
    }

    @Override
    public Sessions getSessionByUser(final int userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT s.* FROM sessions s WHERE s.user_id = ?", new SessionsMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Sessions getSessionById(String sessionId) {
        try {
            return jdbcTemplate.queryForObject("SELECT s.* FROM sessions s WHERE s.session_id = ?", new SessionsMapper(), sessionId);
        } catch (DataAccessException e) {
            return null;
        }
    }
}
