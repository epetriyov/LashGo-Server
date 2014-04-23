package main.java.com.check.mappers;

import main.java.com.check.domain.Users;
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
        users.setPassword(resultSet.getString("password"));
        users.setName(resultSet.getString("name"));
        users.setSurname(resultSet.getString("surname"));
        users.setAbout(resultSet.getString("about"));
        users.setCity(resultSet.getString("city"));
        users.setBirthDate(resultSet.getTimestamp("birth_date"));
        users.setAvatar(resultSet.getString("avatar"));
        users.setEmail(resultSet.getString("email"));
        users.setAdmin(resultSet.getBoolean("is_admin"));
        return users;
    }
}
