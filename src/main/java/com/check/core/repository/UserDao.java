package main.java.com.check.core.repository;

import main.java.com.check.core.domain.Users;
import main.java.com.check.rest.dto.LoginInfo;
import org.springframework.security.core.userdetails.User;

/**
 * Created by Eugene on 12.02.14.
 */
public interface UserDao {

    void removeAllUsers();

    int getCount();

    Users findUser(LoginInfo loginInfo);

    void createUser(LoginInfo loginInfo);
}
