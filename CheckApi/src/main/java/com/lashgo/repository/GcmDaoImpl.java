package com.lashgo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

/**
 * Created by Eugene on 19.03.14.
 */
@Repository
public class GcmDaoImpl implements GcmDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isRegistrationIdExists(String registrationId) {
        try {
            Integer registrationIdsCount = jdbcTemplate.queryForObject("SELECT COUNT(gr.registration_id) FROM gcm_registrations gr WHERE gr.registration_id = ?",
                    Integer.class, registrationId);
            return registrationIdsCount > 0;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getAllRegistrationIds() {
        return jdbcTemplate.queryForList("SELECT gr.registration_id FROM gcm_registrations gr", String.class);
    }

    @Override
    public void addRegistrationId(String registrationId, int userId) {
        jdbcTemplate.update("INSERT INTO gcm_registrations (registration_id,user_id) VALUES (?,?)", registrationId, userId);
    }
}
