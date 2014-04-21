package main.java.com.check.repository;

import main.java.com.check.domain.Check;
import main.java.com.check.mappers.CheckMapper;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.ValidationException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
@Repository
public class CheckDaoImpl implements CheckDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Override
    public Check getNextCheck() {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE c.start_date + c.duration > current_date ORDER BY c.start_date ASC", new CheckMapper());
        } catch (DataAccessException e) {
            logger.info("There are no current check");
            return null;
        }
    }

    @Override
    public List<Check> getAllChecks() {
        return jdbcTemplate.query("SELECT c.* FROM checks c ORDER BY c.start_date desc", new CheckMapper());
    }
}
