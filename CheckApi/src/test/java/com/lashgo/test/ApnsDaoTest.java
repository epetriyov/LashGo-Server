package com.lashgo.test;


import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.ApnDao;
import com.lashgo.repository.UserDao;
import com.lashgo.test.components.TestHelper;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;

import static org.testng.Assert.*;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ApnsDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ApnDao apnDao;

    @Autowired
    private UserDao userDao;

    private LoginInfo loginInfo;

    private String token;

    @Autowired
    private TestHelper testHelper;

    @BeforeClass
    public void initLoginInfo() {
        loginInfo = new LoginInfo("test", CheckUtils.md5("test"));
        token = "test";
    }

    public void testGetAllApnsTokens() {
        assertTrue(apnDao.getAllApnsTokens().isEmpty());
    }

    public void testIsApnsTokenExists() {
        assertFalse(apnDao.isApnsTokenExists(""));
    }

    public void testGetRegisterDateByToken() {
        assertNull(apnDao.getRegisterDateByToken(""));
    }

    @Rollback
    public void testAddApnsToken() {
        testHelper.addApns(loginInfo, token);
    }

    @Test(expectedExceptions = DuplicateKeyException.class)
    @Rollback
    public void testDublicateAddApnsToken() {
        testHelper.addApns(loginInfo, token);
        apnDao.addApnsToken(token, userDao.findUser(loginInfo).getId());
    }

    @Rollback
    public void testUpdateRegisterDate() {
        testHelper.addApns(loginInfo, token);
        Date beforeDate = apnDao.getRegisterDateByToken(token);
        try {
            Thread.currentThread().sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        apnDao.updateRegisterDate(token);
        Date afterDate = apnDao.getRegisterDateByToken(token);
        assertTrue(afterDate.after(beforeDate));
    }

    @Rollback
    public void testRemoveInactiveTokens() {
        testHelper.addApns(loginInfo, token);
        apnDao.removeInactiveTokens(Arrays.asList(new String[]{token}));
        assertFalse(apnDao.isApnsTokenExists(token));
    }
}
