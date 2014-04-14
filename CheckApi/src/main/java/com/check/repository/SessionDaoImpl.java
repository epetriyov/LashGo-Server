package main.java.com.check.repository;

import main.java.com.check.domain.Sessions;
import main.java.com.check.mappers.SessionsMapper;
import main.java.com.check.utils.CheckUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.02.14.
 */
@Repository
public class SessionDaoImpl implements SessionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Sessions createSession(final int userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("INSERT INTO Sessions (session_id, user_id,start_time) VALUES (?,?,?)");
                        ps.setString(1, generateSession(userId));
                        ps.setInt(2, userId);
                        ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                        return ps;
                    }
                },
                keyHolder
        );
        return jdbcTemplate.queryForObject("SELECT s.* FROM Sessions s WHERE s.id = ?", new SessionsMapper(), keyHolder.getKey());
    }


    private String generateSession(int userId) {
        return CheckUtils.md5(String.valueOf(userId) + new Date().getTime());
    }

    @Override
    public Sessions getSession(int userId) {
        return jdbcTemplate.queryForObject("SELECT s.* FROM Sessions WHERE user_id = ?", new SessionsMapper(), userId);
    }

    @Override
    public long getUserBySession(String sessionId) {
        return jdbcTemplate.queryForObject("SELECT s.user_id FROM sessions s WHERE s.session_id = ?", Long.class, sessionId);
    }
}
