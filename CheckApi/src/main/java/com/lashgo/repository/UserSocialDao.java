package com.lashgo.repository;

/**
 * Created by Eugene on 14.09.2014.
 */
public interface UserSocialDao {

    void createUserSocial(int userId, String socialLogin);

    int getUserBySocial(String socialLogin);
}
