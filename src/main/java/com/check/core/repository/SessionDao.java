package main.java.com.check.core.repository;

/**
 * Created by Eugene on 14.02.14.
 */
public interface SessionDao {

    String saveSession(String uuid, int userId);

}
