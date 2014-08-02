package com.lashgo.repository;

import com.lashgo.mappers.CheckMapper;
import com.lashgo.model.dto.CheckDto;
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
 * Created by Eugene on 23.03.2014.
 */
@Repository
public class CheckDaoImpl implements CheckDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Override
    public CheckDto getNextCheck() {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE c.start_date + c.duration * INTERVAL '1 hour' > current_date ORDER BY c.start_date ASC", new CheckMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("checks.current.empty", null, Locale.ENGLISH));
            return null;
        }
    }

    @Override
    public List<CheckDto> getAllChecks() {
        return jdbcTemplate.query("SELECT c.*,p.picture AS check_photo FROM checks c LEFT JOIN photos p ON (p.check_id = c.id) ORDER BY c.start_date DESC", new CheckMapper());
    }

    @Override
    public CheckDto getCheckById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE c.id = ?", new CheckMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("checks.empty", new Object[]{id}, Locale.ENGLISH));
            return null;
        }
    }

    @Override
    public CheckDto getLastCheck() {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE c.start_date < current_date ORDER BY c.start_date LIMIT 1 ASC", new CheckMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("checks.current.empty", null, Locale.ENGLISH));
            return null;
        }
    }

    @Override
    public int getActiveChecksCount() {
        try {
            return jdbcTemplate.queryForObject("SELECT count(c.id) FROM checks c WHERE c.start_date + c.duration * INTERVAL '1 hour' > current_date", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("checks.current.empty", null, Locale.ENGLISH));
            return 0;
        }
    }
}
