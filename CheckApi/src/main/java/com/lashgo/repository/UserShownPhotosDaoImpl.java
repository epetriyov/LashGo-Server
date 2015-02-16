package com.lashgo.repository;

import com.lashgo.mappers.UserVotesMapper;
import com.lashgo.model.dto.VotePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by Eugene on 31.08.2014.
 */
@Repository
public class UserShownPhotosDaoImpl implements UserShownPhotosDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<VotePhoto> getUserShownPhotos(int userId, int checkId) {
        List<VotePhoto> votePhotos = jdbcTemplate.query(
                "SELECT p.id as id_photo, p.picture, u.id, u.login, u.fio,u.avatar," +
                        "(SELECT COUNT (com.id) FROM photo_comments com WHERE com.photo_id = p.id) AS comments_count," +
                        "(SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = p.id) AS likes_count," +
                        "(SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = p.id AND uv.user_id = ?) AS user_votes_count," +
                        "(SELECT COUNT (us.id) FROM user_shown_photos us WHERE us.photo_id = p.id AND us.user_id = ?) AS user_shown_count" +
                        "                    FROM photos p, users u " +
                        "                   WHERE p.user_id = u.id AND p.check_id = ? AND p.is_banned = 0 " +
                        "ORDER BY p.make_date", new UserVotesMapper(), userId, userId, checkId);
        if (votePhotos != null) {
            Collections.shuffle(votePhotos);
        }
        return votePhotos;
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
