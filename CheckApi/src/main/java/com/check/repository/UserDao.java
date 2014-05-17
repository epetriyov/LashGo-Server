package main.java.com.check.repository;

import com.check.model.dto.RegisterInfo;
import com.check.model.dto.UserDto;
import main.java.com.check.domain.Users;
import com.check.model.dto.LoginInfo;

import java.util.List;

/**
 * Created by Eugene on 12.02.14.
 */
public interface UserDao {

    void removeAllUsers();

    int getCount();

    Users findUser(LoginInfo loginInfo);

    void createUser(RegisterInfo registerInfo);

    boolean isUserExists(String login);

    Users getUserById(long userId);
}
