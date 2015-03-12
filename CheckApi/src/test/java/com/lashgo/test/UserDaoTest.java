package com.lashgo.test;

import com.lashgo.domain.Users;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.RegisterInfo;
import com.lashgo.repository.UserDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by Eugene on 13.03.2015.
 */
@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Rollback
    public void testCreateUserByLoginInfo() {
        Number userId = userDao.createUser(new LoginInfo("test", CheckUtils.md5("test")));
        Users userDto = userDao.getUserById(userId.intValue());
        assertNotNull(userDto);
        assertEquals(userDto.getLogin(), "test");
        assertEquals(userDto.getEmail(), "test");
        assertEquals(userDto.getPassword(), CheckUtils.md5("test"));
    }

    @Rollback
    public void testCreateUserByRegisterInfo() {
        Number userId = userDao.createUser(new RegisterInfo("test", CheckUtils.md5("test"), "email"));
        Users userDto = userDao.getUserById(userId.intValue());
        assertNotNull(userDto);
        assertEquals(userDto.getLogin(), "test");
        assertEquals(userDto.getEmail(), "email");
        assertEquals(userDto.getPassword(), CheckUtils.md5("test"));
    }
}
