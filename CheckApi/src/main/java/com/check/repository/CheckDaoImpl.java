package main.java.com.check.repository;

import main.java.com.check.domain.Check;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
@Repository
public class CheckDaoImpl implements CheckDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Check getNextCheck() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Check where expireDate > current_date order by expireDate asc");
        query.setMaxResults(1);
        List<Check> checks = query.list();
        if (!CollectionUtils.isEmpty(checks)) {
            return checks.get(0);
        }
        return null;
    }

    @Override
    public List<Check> getAllChecks() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Check order by expireDate desc ");
        return query.list();
    }
}
