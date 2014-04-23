package main.java.com.check.repository;

import main.java.com.check.domain.Sessions;
import main.java.com.check.mappers.SessionsMapper;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.utils.CheckUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Eugene on 14.02.14.
 */
@Repository
public class SessionDaoImpl implements SessionDao {

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MessageSource messageSource;

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
    public Sessions getSessionByUser(int userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT s.* FROM sessions s WHERE s.user_id = ?", new SessionsMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("user.session.not.exists", new Integer[]{userId}, Locale.ENGLISH));
            return null;
        }
    }

    @Override
    public Sessions getSessionById(String sessionId) {
        try {
            return jdbcTemplate.queryForObject("SELECT s.* FROM sessions s WHERE s.session_id = ?", new SessionsMapper(), sessionId);
        } catch (DataAccessException e) {
            logger.info(messageSource.getMessage("session.not.exists", new String[]{sessionId}, Locale.ENGLISH));
            return null;
        }
    }
}
