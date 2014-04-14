package main.java.com.check.repository;

import main.java.com.check.domain.Sessions;

/**
 * Created by Eugene on 14.02.14.
 */
public interface SessionDao {

    Sessions createSession(int userId);

    Sessions getSession(int userId);

    long getUserBySession(String sessionId);
}
