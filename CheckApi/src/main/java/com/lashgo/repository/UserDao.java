package main.java.com.lashgo.repository;

import com.lashgo.model.dto.RegisterInfo;
import main.java.com.lashgo.domain.Users;
import com.lashgo.model.dto.LoginInfo;

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
