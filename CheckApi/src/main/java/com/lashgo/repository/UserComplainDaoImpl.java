package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 30.10.2014.
 */
@Repository
public class UserComplainDaoImpl implements UserComplainDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void makeComplain(int userId, long photoId) {
        jdbcTemplate.update("INSERT INTO user_complains (user_id, photo_id) VALUES (?,?)", userId, photoId);
    }

    @Override
    public boolean isComplainExists(int userId, long photoId) {
        return jdbcTemplate.queryForObject("SELECT count(id) FROM user_complains WHERE user_id = ? AND photo_id = ?", Integer.class, userId, photoId) > 0;
    }
}
