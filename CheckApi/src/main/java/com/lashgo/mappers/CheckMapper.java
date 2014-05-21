package main.java.com.lashgo.mappers;

import main.java.com.lashgo.domain.Check;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 13.04.2014.
 */
public class CheckMapper implements org.springframework.jdbc.core.RowMapper<main.java.com.lashgo.domain.Check> {
    @Override
    public Check mapRow(ResultSet resultSet, int i) throws SQLException {
        Check check = new Check();
        check.setId(resultSet.getInt("id"));
        check.setName(resultSet.getString("name"));
        check.setDescription(resultSet.getString("description"));
        check.setStartDate(resultSet.getTimestamp("start_date"));
        check.setDuration(resultSet.getInt("duration"));
        check.setPhoto(resultSet.getString("photo"));
        return check;
    }
}
