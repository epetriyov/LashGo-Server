package com.lashgo.repository;

import com.lashgo.domain.Users;
import com.lashgo.mappers.SubscriptionsMapper;
import com.lashgo.mappers.UserDtoMapper;
import com.lashgo.mappers.UsersMapper;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.RegisterInfo;
import com.lashgo.model.dto.SubscriptionDto;
import com.lashgo.model.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Eugene on 12.02.14.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(u.*) FROM users u", Integer.class);
    }

    @Override
    public Users findUser(LoginInfo loginInfo) {
        try {
            return jdbcTemplate.queryForObject("SELECT u.* FROM users u WHERE (u.login = ? OR u.email = ?) AND u.password = ?", new UsersMapper(), loginInfo.getLogin(), loginInfo.getLogin(), loginInfo.getPasswordHash());
        } catch (EmptyResultDataAccessException e) {
            return null;

        }
    }

    public boolean isUserExists(String email) {
        try {
            jdbcTemplate.queryForObject("SELECT u.id FROM users u WHERE u.email = ? OR u.login = ?", Integer.class, email, email);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public Users getUserById(int userId) {
        return jdbcTemplate.queryForObject("SELECT u.* FROM users u WHERE u.id = ?", new UsersMapper(), userId);
    }

    @Override
    public Number createUser(LoginInfo registerInfo) {
        return createUser(new RegisterInfo(registerInfo.getLogin(),registerInfo.getPasswordHash(),registerInfo.getLogin()));
    }

    @Override
    public Number createUser(RegisterInfo registerInfo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> params = new HashMap<>();
        params.put("login", registerInfo.getLogin());
        params.put("password", registerInfo.getPasswordHash());
        params.put("fio", registerInfo.getFio());
        params.put("about", registerInfo.getAbout());
        params.put("city", registerInfo.getCity());
        params.put("birth_date", registerInfo.getBirthDate());
        params.put("avatar", registerInfo.getAvatar());
        params.put("email", registerInfo.getEmail());
        return simpleJdbcInsert.withTableName("users").usingGeneratedKeyColumns("id").usingColumns("login", "password", "fio","about","city","birth_date","avatar","email").executeAndReturnKey(params);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        jdbcTemplate.update("UPDATE users SET password = ? WHERE email = ?", newPassword, email);
    }

    public UserDto getUserProfile(int currentUserId, int userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT u.* ," +
                            " (SELECT COUNT(ph2.id) FROM photos ph2 WHERE (ph2.user_id = u.id)) AS checks_count," +
                            " (SELECT COUNT(comm.id) FROM comments comm WHERE (comm.user_id = u.id)) AS comments_count," +
                            " (SELECT COUNT(likes.id) FROM user_photo_likes likes WHERE (likes.user_id = u.id)) AS likes_count," +
                            " (SELECT COUNT(subs.id) FROM subscriptions subs WHERE (subs.user_id = u.id)) AS user_subscribes," +
                            " (SELECT COUNT(subs2.id) FROM subscriptions subs2 WHERE (subs2.checklist_id = u.id)) AS user_subscribers," +
                            " (SELECT COUNT(subs3.id) FROM subscriptions subs3 WHERE (subs3.user_id = ? AND subs3.checklist_id = u.id)) AS is_subscription" +
                            "  FROM users u" +
                            " WHERE u.id = ?" +
                            " GROUP BY u.id"
                    , new UserDtoMapper(true), currentUserId, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
        jdbcTemplate.update(sql, args.toArray(new Object[args.size()])
        );
    }

    @Override
    public boolean isUserExists(int userId, String email) {
        try {
            jdbcTemplate.queryForObject("SELECT u.id FROM users u WHERE (u.email = ? OR u.login = ?) AND u.id <> ?", Integer.class, email, email, userId);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public UserDto getUserProfile(int userId) {
        return getUserProfile(-1, userId);
    }

    @Override
    public List<SubscriptionDto> findUsers(String searchText, int userId) {
        if (searchText != null) {
            return jdbcTemplate.query("" +
                    "SELECT u.id as uid,u.login,u.fio,u.avatar, " +
                    "COUNT(sub.id) AS sub_count " +
                    "FROM users u LEFT JOIN subscriptions sub " +
                    "ON (sub.user_id = ? AND sub.checklist_id= u.id) " +
                    "WHERE u.id != ? AND (LOWER(u.login) LIKE ? OR LOWER(u.fio) LIKE ? OR LOWER(u.email) LIKE ?) GROUP BY u.id"
                    , new SubscriptionsMapper(), userId, userId, "%" + searchText.toLowerCase() + "%", "%" + searchText.toLowerCase() + "%", "%" + searchText.toLowerCase() + "%");
        }
        return Collections.emptyList();
    }

    @Override
    public List<SubscriptionDto> getUsersByCheck(int userId, int checkId) {
        return jdbcTemplate.query("" +
                "SELECT u.id as uid,u.login,u.fio,u.avatar, " +
                "COUNT(sub.id) AS sub_count " +
                "FROM users u  INNER JOIN photos p ON (p.user_id = u.id) " +
                "INNER JOIN checks c ON (c.id = p.check_id AND c.id = ?) LEFT JOIN subscriptions sub " +
                "ON (sub.user_id = ? AND sub.checklist_id= u.id) GROUP BY u.id,u.login,u.fio,u.avatar"
                , new SubscriptionsMapper(), checkId, userId);
    }

    @Override
    public List<SubscriptionDto> getUsersByVotes(int userId, long photoId) {
        return jdbcTemplate.query(
                "SELECT u.id as uid,u.login,u.fio,u.avatar, " +
                        "COUNT(sub.id) AS sub_count " +
                        "FROM users u  INNER JOIN user_votes uv ON (uv.photo_id = ? AND uv.user_id = u.id) LEFT JOIN subscriptions sub " +
                        "ON (sub.user_id = ? AND sub.checklist_id= u.id) GROUP BY u.id,u.login,u.fio,u.avatar"
                , new SubscriptionsMapper(), photoId, userId);
    }
}
