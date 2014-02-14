package main.java.com.check.core.repository;

import main.java.com.check.core.domain.Users;
import main.java.com.check.rest.dto.LoginInfo;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Eugene on 12.02.14.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void removeAllUsers() {
        sessionFactory.getCurrentSession().createQuery("delete from Users").executeUpdate();
    }

    @Override
    public int getCount() {
        return ((Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Users").uniqueResult()).intValue();
    }

    @Override
    public Users findUser(LoginInfo loginInfo) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Users where login = :login and passwordHash = :passwordHash");
        query.setParameter("login", loginInfo.getLogin());
        query.setParameter("passwordHash", loginInfo.getPasswordHash());
        List<Users> userList = query.list();
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public void createUser(LoginInfo loginInfo) {
        sessionFactory.getCurrentSession().save(new Users(loginInfo));
    }
}
