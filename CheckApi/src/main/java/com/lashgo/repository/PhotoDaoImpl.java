package com.lashgo.repository;

import com.lashgo.domain.Photos;
import com.lashgo.mappers.CheckCountersMapper;
import com.lashgo.mappers.PhotoMapper;
import com.lashgo.mappers.PhotosDtoMapper;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.PhotoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eugene on 28.04.2014.
 */
@Repository
public class PhotoDaoImpl implements PhotoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Number savePhoto(Photos photos) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> params = new HashMap<>();
        params.put("picture", photos.getPicture());
        params.put("user_id", photos.getUser().getId());
        params.put("check_id", photos.getCheck().getId());
        return simpleJdbcInsert.withTableName("photos").usingGeneratedKeyColumns("id").usingColumns("picture", "user_id", "check_id").executeAndReturnKey(params);
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
                        " COUNT(w.id) as is_winner, c.id, c.name,c.task_photo," +
                        "(SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = p.id) AS likes_count," +
                        "(SELECT COUNT (com.id) FROM photo_comments com WHERE com.photo_id = p.id) AS comments_count" +
                        "  FROM photos p" +
                        " INNER JOIN checks c ON (p.check_id = c.id)" +
                        "  LEFT JOIN  check_winners w ON (w.check_id = p.check_id AND w.winner_id = p.user_id)" +
                        " WHERE p.user_id = ?" +
                        " GROUP BY p.id,c.id" +
                        " ORDER BY make_date DESC", new PhotosDtoMapper(PhotosDtoMapper.MapType.USER_JOIN), userId);
    }

    public CheckCounters getPhotoCounters(long photoId) {
        return jdbcTemplate.queryForObject(
                "SELECT (SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = ?) AS likes_count," +
                        "(SELECT COUNT (com.id) FROM photo_comments com WHERE com.photo_id = ?) AS comments_count;"
                , new CheckCountersMapper(), photoId, photoId);
    }

    @Override
    public List<PhotoDto> getPhotosByCheckId(int checkId) {
        return jdbcTemplate.query("SELECT p.id as id_photo, p.picture, u.id, u.login, u.fio, u.avatar," +
                "(SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = p.id) AS likes_count," +
                "(SELECT COUNT (com.id) FROM photo_comments com WHERE com.photo_id = p.id) AS comments_count" +
                "                    FROM photos p" +
                "                   INNER JOIN users u ON (u.id = p.user_id)" +
                "                   WHERE p.user_id = u.id AND p.check_id = ? AND p.is_banned = 0 ORDER BY make_date DESC", new PhotosDtoMapper(PhotosDtoMapper.MapType.CHECK_JOIN), checkId);
    }

    @Override
    public PhotoDto getPhotoById(long photoId) {
        try {
            return jdbcTemplate.queryForObject("SELECT p.id as id_photo, p.picture, u.id, u.login, u.fio, u.avatar," +
                    "(SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = p.id) AS likes_count," +
                    "(SELECT COUNT (com.id) FROM photo_comments com WHERE com.photo_id = p.id) AS comments_count " +
                    " FROM photos p " +
                    " INNER JOIN users u ON (u.id = p.user_id)" +
                    " WHERE p.id = ?", new PhotosDtoMapper(PhotosDtoMapper.MapType.CHECK_JOIN), photoId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public PhotoDto getJustPhotoById(long photoId) {
        try {
            return jdbcTemplate.queryForObject("SELECT p.* FROM photos p WHERE id = ?", new PhotoMapper(), photoId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public PhotoDto getPhoto(int checkId, int userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT p.* FROM photos p WHERE p.check_id = ? AND p.user_id = ?", new PhotoMapper(), checkId, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
