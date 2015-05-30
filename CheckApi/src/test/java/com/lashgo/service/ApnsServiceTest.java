package com.lashgo.service;

import com.lashgo.dao.components.TestHelper;
import com.lashgo.domain.Check;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.ApnDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Created by Eugene on 29.04.2015.
 */
@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ApnsServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ApnsService apnsService;

    @Autowired
    private ApnDao apnDao;

    @Autowired
    private TestHelper testHelper;

    @Rollback
    public void testSendApn()
    {
        Number userId = testHelper.addTestUser(new LoginInfo("test","test"));
        apnDao.addApnsToken("116da1c616eb5f23cdcbc3a0ed12e4a163d5fe1cf5068d0df4991f2ae9af4b7d", userId.intValue());
        Check activeCheck = new Check(1);
        activeCheck.setName("test");
        apnsService.sendApn(activeCheck,null, finishedCheck);
    }
}
