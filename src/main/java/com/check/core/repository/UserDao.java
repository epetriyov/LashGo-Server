package main.java.com.check.core.repository;

import main.java.com.check.core.domain.Users;

/**
 * Created by Eugene on 12.02.14.
 */
public interface UserDao {

    void addUser(Users user);

    void removeAllUsers();

    int getCount();
}
