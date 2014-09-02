package com.lashgo.repository;

import com.lashgo.mappers.UserVotesMapper;
import com.lashgo.model.dto.VotePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
    public List<VotePhoto> getUserShownPhotos(int userId, int checkId, int limit) {
        return jdbcTemplate.query(
                " SELECT ph.id AS photo_id,ph.picture AS photo,u.id AS user_id," +
                        "u.login AS user_login,u.avatar AS user_avatar " +
                        "FROM photos ph " +
                        "INNER JOIN users u ON " +
                        "(u.id = ph.user_id) " +
                        "WHERE ph.check_id = ? AND ph.id NOT IN " +
                        "(SELECT uv.photo_id FROM user_shown_photos uv " +
                        " WHERE uv.user_id = ?) LIMIT ?", new UserVotesMapper(), checkId, userId, limit);
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
