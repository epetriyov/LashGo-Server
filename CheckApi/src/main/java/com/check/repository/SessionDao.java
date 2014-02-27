package main.java.com.check.repository;

/**
 * Created by Eugene on 14.02.14.
 */
public interface SessionDao {

    String saveSession(String uuid, int userId);

    String getSession(String uuid, int userId);
}
