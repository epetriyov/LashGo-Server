package com.lashgo.repository;

import com.lashgo.domain.Users;
import com.lashgo.mappers.UsersMapper;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.RegisterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
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
            return jdbcTemplate.queryForObject("SELECT u.* FROM users u WHERE (u.login = ? OR u.email = ?) AND u.password = ?", new UsersMapper(), loginInfo.getLogin(), loginInfo.getLogin(), loginInfo.getPasswordHash());
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage(ErrorCodes.USER_NOT_EXISTS, new String[]{loginInfo.getLogin()}, Locale.ENGLISH));
            return null;

        }
    }

    public boolean isUserExists(String email) {
        try {
            jdbcTemplate.queryForObject("SELECT u.id FROM users u WHERE u.email = ? OR u.login = ?", Integer.class, email, email);
        } catch (DataAccessException e) {
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Users getUserById(long userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT u.* FROM users u WHERE u.id = ?", new UsersMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage(ErrorCodes.USER_NOT_EXISTS, new Long[]{userId}, Locale.ENGLISH));
            return null;
        }
    }

    @Override
    public void createTempUser(RegisterInfo registerInfo) {
        jdbcTemplate.update("INSERT INTO temp_users (login, password, name,surname,about,city,birth_date,avatar,email) " +
                "VALUES (?,?,?,?,?,?,?,?,?)", registerInfo.getLogin(), registerInfo.getPasswordHash(), registerInfo.getName(), registerInfo.getSurname(), registerInfo.getAbout(), registerInfo.getCity(), registerInfo.getBirthDate(), registerInfo.getAvatar(), registerInfo.getEmail());
    }

    @Override
    public void createUser(LoginInfo registerInfo) {
        jdbcTemplate.update("INSERT INTO users (login,password,email) " +
                        "VALUES (?,?,?)", registerInfo.getLogin(), registerInfo.getPasswordHash(),
                registerInfo.getLogin()
        );
    }

    @Override
    public void createUser(RegisterInfo registerInfo) {
        jdbcTemplate.update("INSERT INTO users (login, password, name,surname,about,city,birth_date,avatar,email) " +
                "VALUES (?,?,?,?,?,?,?,?,?)", registerInfo.getLogin(), registerInfo.getPasswordHash(), registerInfo.getName(), registerInfo.getSurname(), registerInfo.getAbout(), registerInfo.getCity(), registerInfo.getBirthDate(), registerInfo.getAvatar(), registerInfo.getEmail());
    }

    @Override
    public Users findTempUser(String userName) {
        try {
            return jdbcTemplate.queryForObject("SELECT u.* FROM temp_users u WHERE u.login = ?", new UsersMapper(), userName);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage(ErrorCodes.USER_NOT_EXISTS, new String[]{userName}, Locale.ENGLISH));
            return null;

        }
    }

    @Override
    public void createSocialUser(Users tempUser) {
        jdbcTemplate.update("INSERT INTO users (login, password, name,surname,about,city,birth_date,avatar,email) " +
                "VALUES (?,?,?,?,?,?,?,?,?)", tempUser.getLogin(), tempUser.getPassword(), tempUser.getName(), tempUser.getSurname(), tempUser.getAbout(), tempUser.getCity(), tempUser.getBirthDate(), tempUser.getAvatar(), tempUser.getEmail());
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        jdbcTemplate.update("UPDATE users SET password = ? WHERE email = ?", newPassword, email);
    }
}
