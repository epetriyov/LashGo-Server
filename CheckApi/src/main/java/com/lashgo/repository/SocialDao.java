package com.lashgo.repository;

/**
 * Created by Eugene on 14.09.2014.
 */
public interface SocialDao {

    boolean isSocialExists(String socialLogin);

    void createSocial(String socialLogin, String accessToken);
}
