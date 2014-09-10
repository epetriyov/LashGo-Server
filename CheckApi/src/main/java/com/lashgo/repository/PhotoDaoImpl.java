package com.lashgo.repository;

import com.lashgo.domain.Photos;
import com.lashgo.mappers.PhotosDtoMapper;
import com.lashgo.model.dto.PhotoDto;
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
        jdbcTemplate.update("INSERT INTO photos (picture, user_id, check_id)" +
                "            VALUES (?,?,?)", photos.getPicture(), photos.getUser().getId(), photos.getCheck().getId());
    }

    public boolean isPhotoExists(int userId, int checkId) {
        Integer photosCount = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM photos" +
                "                    WHERE user_id = ? AND check_id = ?", Integer.class, userId, checkId);
        return photosCount > 0;
    }

    @Override
    public List<PhotoDto> getPhotosByUserId(int userId) {
        return jdbcTemplate.query(
                "SELECT p.id as id_photo, p.picture,p.is_banned," +
                " COUNT(w.id) as is_winner, COUNT(pc.id) AS comments_count," +
                " COUNT(pl.id) AS likes_count, c.id, c.name,c.task_photo" +
                "  FROM photos p" +
                " INNER JOIN checks c ON (p.check_id = c.id)" +
                "  LEFT JOIN photo_comments pc ON (pc.photo_id = p.id)" +
                "  LEFT JOIN user_photo_likes pl ON (pl.photo_id = p.id)" +
                "  LEFT JOIN  check_winners w ON (w.check_id = p.check_id AND w.winner_id = p.user_id)" +
                " WHERE p.user_id = ?" +
                " GROUP BY p.id,c.id" +
                " ORDER BY make_date ASC", new PhotosDtoMapper(PhotosDtoMapper.MapType.USER_JOIN), userId);
    }

    @Override
    public List<PhotoDto> getPhotosByCheckId(int checkId) {
        return jdbcTemplate.query("SELECT p.id as id_photo, p.picture, u.id, u.login, u.avatar," +
                "                         COUNT(pc.id) AS comments_count,COUNT(pl.id) AS likes_count" +
                "                    FROM photos p, users u " +
                "                    LEFT JOIN photo_comments pc ON (pc.photo_id = p.id) " +
                "                    LEFT JOIN user_photo_likes pl ON (pl.photo_id = p.id) " +
                "                   WHERE p.user_id = u.id AND p.check_id = ? AND p.is_banned = 0 ORDER BY make_date ASC", new PhotosDtoMapper(PhotosDtoMapper.MapType.CHECK_JOIN), checkId);
    }
}
