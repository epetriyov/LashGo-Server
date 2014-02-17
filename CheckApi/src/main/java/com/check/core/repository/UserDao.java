package main.java.com.check.core.repository;

import main.java.com.check.core.domain.Users;
import com.check.model.dto.LoginInfo;

/**
 * Created by Eugene on 12.02.14.
 */
public interface UserDao {

    void removeAllUsers();

    int getCount();

    Users findUser(LoginInfo loginInfo);

    void createUser(LoginInfo loginInfo);

    boolean isUserExists(String login);
}
