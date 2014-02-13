package main.java.com.check.core.repository;

import main.java.com.check.core.domain.Users;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 12.02.14.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addUser(Users user) {
        sessionFactory.getCurrentSession().save(user);
//        return jdbcTemplate.update("insert into \"Users\"(login,password_hash) values(?,?)", user.getLogin(), user.getPasswordHash());
    }

    @Override
    public void removeAllUsers() {
        sessionFactory.getCurrentSession().createQuery("delete from Users").executeUpdate();
    }

    @Override
    public int getCount() {
        return ((Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Users").uniqueResult()).intValue();
    }
}
