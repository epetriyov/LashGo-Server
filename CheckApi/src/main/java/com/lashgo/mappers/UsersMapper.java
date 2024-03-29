package com.lashgo.mappers;

import com.lashgo.domain.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 13.04.2014.
 */
public class UsersMapper implements RowMapper<Users> {
    @Override
    public Users mapRow(ResultSet resultSet, int i) throws SQLException {
        Users users = new Users();
        users.setId(resultSet.getInt("id"));
        users.setLogin(resultSet.getString("login"));
        users.setAvatar(resultSet.getString("avatar"));
        users.setPassword(resultSet.getString("password"));
        users.setFio(resultSet.getString("fio"));
        users.setAbout(resultSet.getString("about"));
        users.setCity(resultSet.getString("city"));
        users.setBirthDate(resultSet.getTimestamp("birth_date"));
        users.setEmail(resultSet.getString("email"));
        users.setAdmin(resultSet.getBoolean("is_admin"));
        return users;
    }
}
