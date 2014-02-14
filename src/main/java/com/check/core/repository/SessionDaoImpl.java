package main.java.com.check.core.repository;

import main.java.com.check.core.domain.Sessions;
import main.java.com.check.utils.CheckUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 14.02.14.
 */
@Repository
public class SessionDaoImpl implements SessionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public String saveSession(String uuid, int userId) {
        Sessions session = new Sessions(userId, CheckUtils.generateSession(uuid, userId));
        sessionFactory.getCurrentSession().save(session);
        return session.getSessionId();
    }
}
