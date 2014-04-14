package main.java.com.check.repository;

import main.java.com.check.domain.Check;
import main.java.com.check.mappers.CheckMapper;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Check getNextCheck() {
        return jdbcTemplate.queryForObject("SELECT c.* FROM Checks c WHERE c.expireDate > current_date ORDER BY c.expireDate ASC", new CheckMapper());
    }

    @Override
    public List<Check> getAllChecks() {
        return jdbcTemplate.queryForList("SELECT c.* FROM Checks ORDER BY start_date desc", Check.class);
    }
}
