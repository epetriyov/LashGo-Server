package com.lashgo.dao;

import com.lashgo.dao.components.TestHelper;
import com.lashgo.mappers.EventDtoMapper;
import com.lashgo.model.DbCodes;
import com.lashgo.model.dto.EventDto;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.EventDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by Eugene on 15.03.2015.
 */
@Test(groups = {"eventDao"})
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EventDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private TestHelper testHelper;

    private LoginInfo loginInfo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public void initData() {
        loginInfo = new LoginInfo("test", CheckUtils.md5("test"));
    }

    @Rollback
    public void testAddWinEvent() {
        Number checkId = testHelper.addTestCheck();
        Number userId = testHelper.addTestUser(loginInfo);
        eventDao.addWinEvent(checkId.intValue(), userId.intValue());
        EventDto eventDto = jdbcTemplate.queryForObject("select * from events where user_id = ? and check_id = ?", new EventDtoMapper(), userId.intValue(), checkId.intValue());
        assertNotNull(eventDto);
        assertEquals(eventDto.getAction(), DbCodes.EventActions.WIN.name());
    }

    @Rollback
    public void test() {
        assertEquals(eventDao.getEventsCountByUser(1, null), 0);
        assertEquals(eventDao.getEventsCountByUser(1, new Date()), 0);
    }
}
