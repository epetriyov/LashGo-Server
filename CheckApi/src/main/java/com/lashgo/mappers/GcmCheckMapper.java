package com.lashgo.mappers;

import com.lashgo.domain.Check;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 24.09.2014.
 */
public class GcmCheckMapper implements org.springframework.jdbc.core.RowMapper<Check> {
    @Override
    public Check mapRow(ResultSet resultSet, int i) throws SQLException {
        Check check = new Check();
        check.setId(resultSet.getInt("id"));
        check.setName(resultSet.getString("name"));
        return check;
    }
}
