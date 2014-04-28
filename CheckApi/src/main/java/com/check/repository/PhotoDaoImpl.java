package main.java.com.check.repository;

import main.java.com.check.domain.Photos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 28.04.2014.
 */
@Repository
public class PhotoDaoImpl implements PhotoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void savePhoto(Photos photos) {
        jdbcTemplate.update("INSERT INTO PHOTOS (picture, user_id, check_id)" +
                "            VALUES (?,?,?)", photos.getPicture(), photos.getUser().getId(), photos.getCheck().getId());
    }

    public boolean isPhotoExists(long userId, long checkId) {
        Integer photosCount = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM photos" +
                "                    WHERE user_id = ? AND check_id = ?", Integer.class, userId, checkId);
        return photosCount > 0;
    }

    @Override
    public void unrate(long photoId) {
        jdbcTemplate.update("UPDATE photos SET rating = rating - 1 WHERE photo_id = ?",photoId);
    }

    @Override
    public void rate(long photoId) {
        jdbcTemplate.update("UPDATE photos SET rating = rating + 1 WHERE photo_id = ?",photoId);
    }
}
