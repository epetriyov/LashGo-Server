package com.lashgo.repository;

import com.lashgo.mappers.PhotosDtoMapper;
import com.lashgo.model.dto.PhotoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eugene on 31.08.2014.
 */
@Repository
public class UserShownPhotosDaoImpl implements UserShownPhotosDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<PhotoDto> getUserShownPhotos(int userId, int checkId, int limit) {
        return jdbcTemplate.query(
                "SELECT p.id as id_photo, p.picture, u.id, u.login, u.fio,u.avatar" +
                        "                    FROM photos p, users u " +
                        "                   WHERE p.user_id = u.id AND p.check_id = ? AND p.is_banned = 0 AND p.id NOT IN " +
                        "                        (SELECT uv.photo_id FROM user_shown_photos uv " +
                        "                         WHERE uv.user_id = ?) LIMIT ?", new PhotosDtoMapper(PhotosDtoMapper.MapType.CHECK_JOIN), checkId, userId, limit);
    }

    @Override
    public void addUserShownPhotos(int userId, long[] photoIds) {
        for (int i = 0; i < photoIds.length; i++) {
            jdbcTemplate.update("INSERT INTO user_shown_photos (user_id,photo_id) VALUES (?,?)", userId, photoIds[i]);
        }
    }

    @Override
    public int getUserShownPhotosCount(int userId, int checkId) {
        return jdbcTemplate.queryForObject(
                " SELECT COUNT(ph.id) " +
                        "FROM photos ph " +
                        "INNER JOIN users u ON " +
                        "(u.id = ph.user_id) " +
                        "WHERE ph.check_id = ? AND ph.id NOT IN " +
                        "(SELECT uv.photo_id FROM user_shown_photos uv " +
                        " WHERE uv.user_id = ?)", Integer.class, checkId, userId);
    }
}
