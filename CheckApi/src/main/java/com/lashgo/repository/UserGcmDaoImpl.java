package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 23.06.2014.
 */
@Repository
public class UserGcmDaoImpl implements UserGcmDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addUserRegistration(String registrationId, int userId) {
        jdbcTemplate.update("INSERT INTO gcm_users (registration_id,user_id) VALUES (?,?)", registrationId, userId);
    }

    @Override
    public boolean isUserGcmExists(String registrationId, int userId) {
        try {
            jdbcTemplate.queryForObject("SELECT gu.id FROM gcm_users gu WHERE registration_id = ? AND user_id = ?", Integer.class, registrationId, userId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
