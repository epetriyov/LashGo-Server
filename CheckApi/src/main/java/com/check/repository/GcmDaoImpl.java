package main.java.com.check.repository;

import com.check.model.dto.GcmRegistrationDto;
import main.java.com.check.domain.GcmRegistration;
import main.java.com.check.domain.Users;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 19.03.14.
 */
@Repository
public class GcmDaoImpl implements GcmDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isRegistrationIdExists(String registrationId) {
        String regId = jdbcTemplate.queryForObject("SELECT gr.registration_id FROM gcm_registrations gr WHERE gr.registration_id = ?",
                String.class, registrationId);
        return !StringUtils.isEmpty(regId);
    }

    @Override
    public List<String> getAllRegistrationIds() {
        return jdbcTemplate.queryForList("SELECT gr.registration_id FROM gcm_registrations", String.class);
    }

    @Override
    public void addRegistrationId(long userId, GcmRegistrationDto registrationDto) {
        jdbcTemplate.update("INSERT INTO gcm_registrations VALUES (?,?)", userId, registrationDto.getRegistrationId());
    }
}
