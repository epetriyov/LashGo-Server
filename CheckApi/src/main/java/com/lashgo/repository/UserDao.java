package com.lashgo.repository;

import com.lashgo.model.dto.RegisterInfo;
import com.lashgo.domain.Users;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.UserDto;

/**
 * Created by Eugene on 12.02.14.
 */
public interface UserDao {

    int getCount();

    Users findUser(LoginInfo loginInfo);

    void createUser(LoginInfo registerInfo);

    boolean isUserExists(String email);

    Users getUserById(int userId);

    void createUser(RegisterInfo registerInfo);

    void updatePassword(String email, String newPassword);

    UserDto getUserProfile(int userId);

    void updateAvatar(String photoName, int userId);

    void updateProfile(int userId, UserDto userDto);

    boolean isUserExists(int userId, String email);
}
