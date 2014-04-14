package main.java.com.check.repository;

import com.check.model.dto.RegisterInfo;
import main.java.com.check.domain.Users;
import com.check.model.dto.LoginInfo;
import main.java.com.check.mappers.UsersMapper;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Eugene on 12.02.14.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void removeAllUsers() {
        jdbcTemplate.update("DELETE u.* FROM Users u");
    }

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(u.*) FROM Users u", Integer.class);
    }

    @Override
    public Users findUser(LoginInfo loginInfo) {
        return jdbcTemplate.queryForObject("SELECT u.* FROM Users u WHERE (u.login = ? or u.email = ?) and u.passwordHash = ?", new UsersMapper(), loginInfo.getLogin(), loginInfo.getPasswordHash());
    }

    public boolean isUserExists(String login) {
        Integer usersCount = jdbcTemplate.queryForObject("SELECT u.* FROM Users u WHERE u.login = ?", Integer.class);
        return usersCount > 0;
    }

    @Override
    public void createUser(RegisterInfo registerInfo) {
        jdbcTemplate.update("INSERT INTO Users (login,password,name,surname,about,city,birth_date,avatar,email) " +
                "VALUES (?,?,?,?,?,?,?,?,?)", new Object[]{registerInfo.getLogin(), registerInfo.getPasswordHash(),
                registerInfo.getName(), registerInfo.getSurname(), registerInfo.getAbout(), registerInfo.getBirthDate(),
                registerInfo.getAvatar(), registerInfo.getEmail()});
    }
}
