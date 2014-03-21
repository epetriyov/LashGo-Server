package main.java.com.check.repository;

import main.java.com.check.domain.Sessions;

/**
 * Created by Eugene on 14.02.14.
 */
public interface SessionDao {

    Sessions saveSession(String uuid, int userId);

    Sessions getSession(String uuid, int userId);
}
