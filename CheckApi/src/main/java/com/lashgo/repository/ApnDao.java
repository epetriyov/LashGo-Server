package com.lashgo.repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 22.02.2015.
 */
public interface ApnDao {

    void addApnsToken(String apnsToken, int userId);

    boolean isApnsTokenExists(String apnsToken);

    List<String> getAllApnsTokens();

    Date getRegisterDateByToken(String apnsToken);

    void removeInactiveTokens(List<String> tokens);

    void updateRegisterDate(String token);
}
