package com.lashgo.repository;

import com.lashgo.mappers.UserVotesMapper;
import com.lashgo.model.dto.VotePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by Eugene on 25.08.2014.
 */

@Repository
public class UserVotesDaoImpl implements UserVotesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<VotePhoto> getVotePhotos(int userId, int checkId, int limit) {
        try {
            return jdbcTemplate.query(
                    " SELECT ph.id as photo_id,ph.picture AS photo,u.id AS user_id," +
                            "u.login AS user_login,u.avatar AS user_avatar " +
                            "FROM photos ph " +
                            "INNER JOIN users u ON " +
                            "(u.id = ph.user_id) " +
                            "WHERE ph.check_id = ? AND ph.id NOT IN " +
                            "(SELECT uv.photo_id FROM user_votes uv " +
                            " WHERE uv.user_id = ?) LIMIT ?", new UserVotesMapper(), checkId, userId, limit);
        } catch (EmptyResultDataAccessException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void addUserVotes(int userId, long[] photoIds) {
        for (int i = 0; i < photoIds.length; i++) {
            jdbcTemplate.update("INSERT INTO user_votes (user_id,photo_id) VALUES (?,?)", userId, photoIds[i]);
        }
    }
}
