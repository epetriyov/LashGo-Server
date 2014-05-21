package main.java.com.lashgo.repository;

import com.lashgo.model.dto.RegisterInfo;
import main.java.com.lashgo.domain.Users;
import com.lashgo.model.dto.LoginInfo;
import main.java.com.lashgo.mappers.UsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Locale;

/**
 * Created by Eugene on 12.02.14.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Override
    public void removeAllUsers() {
        jdbcTemplate.update("DELETE u.* FROM users u");
    }

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(u.*) FROM users u", Integer.class);
    }

    @Override
    public Users findUser(LoginInfo loginInfo) {
        try {
            return jdbcTemplate.queryForObject("SELECT u.* FROM users u WHERE (u.login = ? or u.email = ?) and u.password = ?", new UsersMapper(), loginInfo.getLogin(), loginInfo.getLogin(), loginInfo.getPasswordHash());
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("user.not.exists", new String[]{loginInfo.getLogin()}, Locale.ENGLISH));
            return null;

        }
    }

    public boolean isUserExists(String login) {
        Integer usersCount = 0;
        try {
            usersCount = jdbcTemplate.queryForObject("SELECT count(u.id) FROM users u WHERE u.login = ?", Integer.class, login);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("user.not.exists", new String[]{login}, Locale.ENGLISH));
        }
        return usersCount > 0;
    }

    @Override
    public Users getUserById(long userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT u.* FROM users u WHERE u.id = ?", new UsersMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage("user.not.exists", new Long[]{userId}, Locale.ENGLISH));
            return null;
        }
    }

    @Override
    public void createUser(RegisterInfo registerInfo) {
        jdbcTemplate.update("INSERT INTO users (login,password,name,surname,about,city,birth_date,avatar,email) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)", registerInfo.getLogin(), registerInfo.getPasswordHash(),
                registerInfo.getName(), registerInfo.getSurname(), registerInfo.getAbout(), registerInfo.getCity(), registerInfo.getBirthDate(),
                registerInfo.getAvatar(), registerInfo.getEmail()
        );
    }
}
