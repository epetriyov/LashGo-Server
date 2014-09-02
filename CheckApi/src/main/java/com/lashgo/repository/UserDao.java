package com.lashgo.repository;

import com.lashgo.model.dto.RegisterInfo;
import com.lashgo.domain.Users;
import com.lashgo.model.dto.LoginInfo;

/**
 * Created by Eugene on 12.02.14.
 */
public interface UserDao {

    int getCount();

    Users findUser(LoginInfo loginInfo);

    void createUser(LoginInfo registerInfo);

    boolean isUserExists(String email);

    Users getUserById(long userId);

    void createTempUser(RegisterInfo registerInfo);

    void createUser(RegisterInfo registerInfo);

    Users findTempUser(String userName);

    void createSocialUser(Users tempUser);

    void updatePassword(String email, String newPassword);
}
