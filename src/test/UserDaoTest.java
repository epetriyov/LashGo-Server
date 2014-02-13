package test;

import main.java.com.check.core.domain.Users;
import main.java.com.check.core.repository.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

/**
 * Created by Eugene on 12.02.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test/resources/applicationContext.xml")
@TransactionConfiguration
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Before
    public void tearDown() {
        userDao.removeAllUsers();
    }

    @Test
    public void testInsertTopic() {
        userDao.addUser(new Users("test", "test"));
        assertTrue(userDao.getCount() == 1);
    }
}
