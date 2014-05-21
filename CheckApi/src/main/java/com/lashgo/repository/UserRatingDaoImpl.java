package main.java.com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 28.04.2014.
 */
@Repository
public class UserRatingDaoImpl implements UserRatingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean wasRated(int userId, long photoId) {
        Integer ratingsCount = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM user_ratings" +
                "                    WHERE user_id = ? AND photo_id = ?", Integer.class, userId, photoId);
        return ratingsCount > 0;
    }

    @Override
    public void removeRating(int userId, long photoId) {
        jdbcTemplate.update("DELETE FROM user_ratings" +
                "            WHERE user_id = ? AND photo_id = ?", userId, photoId);
    }

    @Override
    public void addRating(int userId, long photoId) {
        jdbcTemplate.update("INSERT INTO user_ratings (user_id,photo_id) VALUES (?,?)", userId, photoId);
    }
}
