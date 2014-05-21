package main.java.com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 28.04.2014.
 */
@Repository
public class UserInterfaceDaoImpl implements UserInterfaceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isUserInterfaceExists(int userId, int interfaceId) {
        Integer userInterfacesCount = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM users_interfaces" +
                "                                                  WHERE user_id = ? AND interface_id = ?", Integer.class, userId, interfaceId);
        return userInterfacesCount > 0;
    }

    @Override
    public void addUserInteface(int userId, int interfaceId) {
        jdbcTemplate.update("INSERT INTO users_interfaces (user_id, interface_id) VALUES (?,?)", userId, interfaceId);
    }
}
