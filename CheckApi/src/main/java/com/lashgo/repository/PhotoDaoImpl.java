package com.lashgo.repository;

import com.lashgo.model.dto.PhotoDto;
import com.lashgo.domain.Photos;
import com.lashgo.mappers.PhotosDtoMapper;
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

    public boolean isPhotoExists(long userId, long checkId) {
        Integer photosCount = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM photos" +
                "                    WHERE user_id = ? AND check_id = ?", Integer.class, userId, checkId);
        return photosCount > 0;
    }

    @Override
    public List<PhotoDto> getPhotosByUserId(int userId) {
        return jdbcTemplate.query("SELECT p.id as id_photo, p.picture, c.id, c.name,c.task_photo FROM photos p, checks c WHERE p.check_id = c.id AND p.user_id = ? ORDER BY make_date ASC", new PhotosDtoMapper(PhotosDtoMapper.MapType.USER_JOIN), userId);
    }

    @Override
    public List<PhotoDto> getPhotosByCheckId(long checkId) {
        return jdbcTemplate.query("SELECT p.id as id_photo, p.picture, u.id, u.login, u.avatar FROM photos p, users u WHERE p.user_id = u.id AND p.user_id = ? ORDER BY make_date ASC", new PhotosDtoMapper(PhotosDtoMapper.MapType.CHECK_JOIN), checkId);
    }
}
