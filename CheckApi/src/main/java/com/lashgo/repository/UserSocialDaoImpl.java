package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 14.09.2014.
 */
@Repository
public class UserSocialDaoImpl implements UserSocialDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createUserSocial(int userId, String socialLogin) {
        jdbcTemplate.update("INSERT INTO social_users (user_id,social_login) VALUES (?,?)", userId, socialLogin);
    }

    @Override
    public int getUserBySocial(String socialLogin) {
        try {
            return jdbcTemplate.queryForObject("SELECT user_id FROM social_users WHERE social_login = ?", Integer.class, socialLogin);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
