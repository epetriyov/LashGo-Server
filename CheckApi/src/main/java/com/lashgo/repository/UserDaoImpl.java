package com.lashgo.repository;

import com.lashgo.domain.Users;
import com.lashgo.mappers.UserDtoMapper;
import com.lashgo.mappers.UsersMapper;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.RegisterInfo;
import com.lashgo.model.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage(ErrorCodes.USER_NOT_EXISTS, new String[]{email}, Locale.ENGLISH));
            return false;
        }
        return true;
    }

    @Override
    public Users getUserById(int userId) {
        return jdbcTemplate.queryForObject("SELECT u.* FROM users u WHERE u.id = ?", new UsersMapper(), userId);
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
        jdbcTemplate.update("INSERT INTO users (login, password, fio,about,city,birth_date,avatar,email) " +
                "VALUES (?,?,?,?,?,?,?,?)", registerInfo.getLogin(), registerInfo.getPasswordHash(), registerInfo.getFio(), registerInfo.getAbout(), registerInfo.getCity(), registerInfo.getBirthDate(), registerInfo.getAvatar(), registerInfo.getEmail());
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        jdbcTemplate.update("UPDATE users SET password = ? WHERE email = ?", newPassword, email);
    }

    public UserDto getUserProfile(int userId) {
        return jdbcTemplate.queryForObject(
                "SELECT u.* ," +
                        " (SELECT COUNT(ph2.id) FROM photos ph2 WHERE (ph2.user_id = u.id)) AS checks_count," +
                        " (SELECT COUNT(comm.id) FROM comments comm WHERE (comm.user_id = u.id)) AS comments_count," +
                        " (SELECT COUNT(likes.id) FROM user_photo_likes likes WHERE (likes.user_id = u.id)) AS likes_count," +
                        " (SELECT COUNT(subs.id) FROM subscriptions subs WHERE (subs.user_id = u.id)) AS user_subscribes," +
                        " (SELECT COUNT(subs2.id) FROM subscriptions subs2 WHERE (subs2.checklist_id = u.id)) AS user_subscribers" +
                        "  FROM users u" +
                        " WHERE u.id = ?" +
                        " GROUP BY u.id"
                , new UserDtoMapper(true), userId);
    }

    @Override
    public void updateAvatar(String photoName, int userId) {
        jdbcTemplate.update("UPDATE users SET avatar = ? WHERE id = ?", photoName, userId);
    }

    @Override
    public void updateProfile(int userId, UserDto userDto) {
        List<Object> args = new ArrayList<>();
        if (userDto.getFio() != null) {
            args.add(userDto.getFio());
        }
        if (userDto.getLogin() != null) {
            args.add(userDto.getLogin());
        }
        if (userDto.getCity() != null) {
            args.add(userDto.getCity());
        }
        if (userDto.getEmail() != null) {
            args.add(userDto.getEmail());
        }
        if (userDto.getAbout() != null) {
            args.add(userDto.getAbout());
        }
        if (userDto.getPasswordHash() != null) {
            args.add(userDto.getPasswordHash());
        }
        args.add(userId);

        String sql =
                "UPDATE users " +
                        (userDto.getFio() != null ? "SET fio = ?" : "") +
                        (userDto.getFio() != null && (userDto.getLogin() != null || userDto.getCity() != null || userDto.getEmail() != null || userDto.getAbout() != null || userDto.getPasswordHash() != null) ? "," : "") +
                        (userDto.getFio() == null ? "SET " : "") +
                        (userDto.getLogin() != null ? " login = ?" : "") +
                        (userDto.getLogin() != null && (userDto.getCity() != null || userDto.getEmail() != null || userDto.getAbout() != null || userDto.getPasswordHash() != null) ? "," : "") +
                        (userDto.getFio() == null && userDto.getLogin() == null ? "SET " : "") +
                        (userDto.getCity() != null ? " city = ?" : "") +
                        (userDto.getCity() != null && (userDto.getEmail() != null || userDto.getAbout() != null || userDto.getPasswordHash() != null) ? "," : "") +
                        (userDto.getFio() == null && userDto.getLogin() == null && userDto.getCity() == null ? "SET " : "") +
                        (userDto.getEmail() != null ? " email = ?" : "") +
                        (userDto.getEmail() != null && (userDto.getAbout() != null || userDto.getPasswordHash() != null) ? "," : "") +
                        (userDto.getFio() == null && userDto.getLogin() == null && userDto.getCity() == null && userDto.getEmail() == null ? "SET " : "") +
                        (userDto.getAbout() != null ? " about = ?" : "") +
                        (userDto.getAbout() != null && userDto.getPasswordHash() != null ? "," : "") +
                        (userDto.getFio() == null && userDto.getLogin() == null && userDto.getCity() == null && userDto.getEmail() == null && userDto.getAbout() == null ? "SET " : "") +
                        (userDto.getPasswordHash() != null ? " password = ?" : "") +
                        "             WHERE id = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, args.toArray(new Object[args.size()])
        );
    }

    @Override
    public boolean isUserExists(int userId, String email) {
        try {
            jdbcTemplate.queryForObject("SELECT u.id FROM users u WHERE (u.email = ? OR u.login = ?) AND u.id <> ?", Integer.class, email, email, userId);
        } catch (EmptyResultDataAccessException e) {
            logger.info(messageSource.getMessage(ErrorCodes.USER_NOT_EXISTS, new String[]{email}, Locale.ENGLISH));
            return false;
        }
        return true;
    }
}
