package main.java.com.check.repository;

import main.java.com.check.domain.Sessions;
import main.java.com.check.utils.CheckUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.02.14.
 */
@Repository
public class SessionDaoImpl implements SessionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Sessions saveSession(String uuid, int userId) {
        Sessions session = new Sessions(userId, CheckUtils.generateSession(uuid, userId), new Date(), uuid);
        sessionFactory.getCurrentSession().save(session);
        return session;
    }

    @Override
    public Sessions  getSession(String uuid, int userId) {
                    Query query = sessionFactory.getCurrentSession().createQuery("from Sessions where userId = :userId and uuid =:uuid");
        query.setParameter("userId", userId);
        query.setParameter("uuid", uuid);
        List<Sessions> sessionsList = query.list();
        if (!CollectionUtils.isEmpty(sessionsList)) {
            return sessionsList.get(0);
        }
        return null;
    }
}
