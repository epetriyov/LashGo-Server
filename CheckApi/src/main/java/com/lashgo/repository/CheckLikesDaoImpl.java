package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 01.09.2014.
 */
@Repository
public class CheckLikesDaoImpl implements CheckLikesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void likeCheck(int userId, int checkId) {
        jdbcTemplate.update("INSERT INTO user_check_likes (user_id,check_id) VALUES (?,?)", userId, checkId);
    }

    @Override
    public void unlikeCheck(int userId, int checkId) {
        jdbcTemplate.update("DELETE FROM user_check_likes where user_id = ? AND check_id = ?", userId, checkId);
    }

    @Override
    public boolean isUserLiked(int userId, int checkId) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM user_check_likes " +
                "WHERE user_id = ? AND check_id = ?", Integer.class, userId, checkId);
        return count > 0;
    }
}
