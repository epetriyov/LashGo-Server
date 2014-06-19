package com.lashgo.repository;

import com.lashgo.model.dto.GcmRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * Created by Eugene on 19.03.14.
 */
@Repository
public class GcmDaoImpl implements GcmDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    public boolean isRegistrationIdExists(String registrationId) {
        try {
            String regId = jdbcTemplate.queryForObject("SELECT gr.registration_id FROM gcm_registrations gr WHERE gr.registration_id = ?",
                    String.class, registrationId);
            return !StringUtils.isEmpty(regId);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("registration_id.not.exists", new String[]{registrationId}, Locale.ENGLISH));
            return false;
        }
    }

    @Override
    public List<String> getAllRegistrationIds() {
        return jdbcTemplate.queryForList("SELECT gr.registration_id FROM gcm_registrations gr", String.class);
    }

    @Override
    public void addRegistrationId(long userId, GcmRegistrationDto registrationDto) {
        jdbcTemplate.update("INSERT INTO gcm_registrations VALUES (?,?)", registrationDto.getRegistrationId(), userId);
    }
}
