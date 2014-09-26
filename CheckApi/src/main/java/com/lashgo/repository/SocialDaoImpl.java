package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 14.09.2014.
 */
@Repository
public class SocialDaoImpl implements SocialDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isSocialExists(String socialLogin) {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(login) FROM socials WHERE login = ?", Integer.class, socialLogin) > 0;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void createSocial(String socialLogin, String accessToken) {
        jdbcTemplate.update("INSERT INTO socials (login,access_token) VALUES (?,?)", socialLogin, accessToken);
    }
}
