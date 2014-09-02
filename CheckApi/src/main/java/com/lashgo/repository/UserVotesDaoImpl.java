package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 25.08.2014.
 */

@Repository
public class UserVotesDaoImpl implements UserVotesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addUserVote(int userId, long photoIds) {
        jdbcTemplate.update("INSERT INTO user_votes (user_id, photo_id) VALUES(?,?)", userId, photoIds);
    }
}
