package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 15.09.2014.
 */
@Repository
public class PhotoLikesDaoImpl implements PhotoLikesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void likeCheck(int userId, long photoId) {
        jdbcTemplate.update("INSERT INTO user_photo_likes (user_id,photo_id) VALUES (?,?)", userId, photoId);
    }

    @Override
    public void unlikeCheck(int userId, long photoId) {
        jdbcTemplate.update("DELETE FROM user_photo_likes where user_id = ? AND photo_id = ?", userId, photoId);
    }

    @Override
    public boolean isUserLiked(int userId, long photoId) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM user_photo_likes " +
                "WHERE user_id = ? AND photo_id = ?", Integer.class, userId, photoId);
        return count > 0;
    }
}
