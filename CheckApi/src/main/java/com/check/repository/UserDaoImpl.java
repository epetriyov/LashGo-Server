package main.java.com.check.repository;

import com.check.model.dto.RegisterInfo;
import com.check.model.dto.SocialInfo;
import main.java.com.check.domain.Users;
import com.check.model.dto.LoginInfo;
import main.java.com.check.utils.CheckUtils;
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

    public boolean isUserExists(String login) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Users where login = :login");
        query.setParameter("login", login);
        List<Users> users = query.list();
        return !CollectionUtils.isEmpty(users);
    }

    @Override
    public Users findSocialUser(String socialLogin, String socialType) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Users where socialLogin = :socialLogin and socialType = :socialType");
        query.setParameter("socialLogin", socialLogin);
        query.setParameter("socialType", socialType);
        List<Users> users = query.list();
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }

    @Override
    public void createUser(RegisterInfo registerInfo) {
        sessionFactory.getCurrentSession().save(new Users(registerInfo));
    }

    @Override
    public LoginInfo createUser(SocialInfo socialInfo) {
        String userName = socialInfo.getSocialType() + socialInfo.getUserName();
        String userPassword = CheckUtils.md5(CheckUtils.md5(userName));
        Users user = new Users();
        user.setAvatarName(socialInfo.getAvatarUrl());
        user.setBirthDate(socialInfo.getBirthDay());
        user.setEmail(socialInfo.getEmail());
        user.setLogin(userName);
        user.setPasswordHash(userPassword);
        user.setName(socialInfo.getName());
        user.setSurname(socialInfo.getSurname());
        user.setSocialLogin(socialInfo.getUserName());
        user.setSocialType(socialInfo.getSocialType());
        sessionFactory.getCurrentSession().save(user);
        return new LoginInfo(userName, userPassword);
    }
}
