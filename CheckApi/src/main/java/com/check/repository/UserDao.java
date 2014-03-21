package main.java.com.check.repository;

import com.check.model.dto.RegisterInfo;
import com.check.model.dto.SocialInfo;
import main.java.com.check.domain.Users;
import com.check.model.dto.LoginInfo;

/**
 * Created by Eugene on 12.02.14.
 */
public interface UserDao {

    void removeAllUsers();

    int getCount();

    Users findUser(LoginInfo loginInfo);

    void createUser(RegisterInfo registerInfo);

    void createUser(SocialInfo socialInfo);

    boolean isUserExists(String login);
}
