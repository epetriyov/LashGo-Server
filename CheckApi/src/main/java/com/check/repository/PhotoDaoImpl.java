package main.java.com.check.repository;

import com.check.model.dto.PhotoDto;
import main.java.com.check.domain.Photos;
import main.java.com.check.mappers.PhotosDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        jdbcTemplate.update("UPDATE photos SET rating = rating - 1 WHERE photo_id = ?", photoId);
    }

    @Override
    public void rate(long photoId) {
        jdbcTemplate.update("UPDATE photos SET rating = rating + 1 WHERE photo_id = ?", photoId);
    }

    @Override
    public List<PhotoDto> getPhotosByUserId(int userId) {
        return jdbcTemplate.query("SELECT p.id as id_photo, p.picture, p.rating, c.id, c.name FROM photos p, checks c WHERE p.check_id = c.id AND p.user_id = ? ORDER BY make_date ASC", new PhotosDtoMapper(PhotosDtoMapper.MapType.USER_JOIN), userId);
    }

    @Override
    public List<PhotoDto> getPhotosByCheckId(long checkId) {
        return jdbcTemplate.query("SELECT p.id as id_photo, p.picture, p.rating, u.id, u.login, u.avatar FROM photos p, users u WHERE p.user_id = u.id AND p.user_id = ? ORDER BY make_date ASC", new PhotosDtoMapper(PhotosDtoMapper.MapType.CHECK_JOIN), checkId);
    }
}
