package com.lashgo.test;

import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.CheckWinnersDao;
import com.lashgo.test.components.TestHelper;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Eugene on 11.03.2015.
 */
@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CheckDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private CheckWinnersDao checkWinnersDao;

    @Autowired
    private TestHelper testHelper;

    private LoginInfo loginInfo;

    @BeforeClass
    public void initData() {
        loginInfo = new LoginInfo("test", CheckUtils.md5("test"));
    }

    @Rollback
    public void testGetAllChecks() {
        assertEquals(checkDao.getAllStartedChecks(-1, null).size(), 0);
        assertEquals(checkDao.getAllStartedChecks(-1, "test").size(), 0);
        Number checkId = testHelper.addTestCheck();
        assertEquals(checkDao.getAllStartedChecks(-1, "test").size(), 1);
        assertEquals(checkDao.getAllStartedChecks(-1, "set").size(), 1);
        assertEquals(checkDao.getAllStartedChecks(-1, "").size(), 1);
        Number userId = testHelper.addTestUser(loginInfo);
        Number photoId = testHelper.addTestPhoto(checkId, userId);
        checkWinnersDao.addCheckWinner(checkId.intValue());
        List<CheckDto> checkDtoList = checkDao.getAllStartedChecks(userId.intValue(), null);
        assertEquals(checkDtoList.size(), 1);
        CheckDto checkDto = checkDtoList.iterator().next();
        testHelper.assertCheckDto(checkDto, userId.intValue(), photoId.longValue());
    }

    @Rollback
    public void testAddNewCheck() {
        Date startDate = Calendar.getInstance().getTime();
        Number checkId = checkDao.addNewCheck(new CheckDto("test", "set", startDate, 48, 10, "task"));
        CheckDto checkDto = checkDao.getCheckById(checkId.intValue());
        assertEquals(checkDto.getName(), "test");
        assertEquals(checkDto.getDescription(), "set");
        assertEquals(checkDto.getStartDate(), startDate);
        assertEquals(checkDto.getDuration(), 48);
        assertEquals(checkDto.getVoteDuration(), 10);
        assertEquals(checkDto.getTaskPhotoUrl(), "task");
    }

    @Rollback
    public void testDoesCheckNameExists() {
        testHelper.addTestCheck();
        assertTrue(checkDao.doesCheckNameExists("test"));
    }

    @Rollback
    public void testGetActiveChecksCount() {
        testHelper.addTestCheck();
        assertEquals(checkDao.getActiveChecksCount(), 1);
    }

    @Rollback
    public void testGetCheckById() {
        Number checkId = testHelper.addTestCheck();
        Number userId = testHelper.addTestUser(loginInfo);
        Number photoId = testHelper.addTestPhoto(checkId, userId);
        checkWinnersDao.addCheckWinner(checkId.intValue());
        CheckDto checkDto = checkDao.getCheckById(userId.intValue(), checkId.intValue());
        assertNotNull(checkDto);
        testHelper.assertCheckDto(checkDto, userId.intValue(), photoId.longValue());
    }

    @Rollback
    public void testGetCheckCounters() {
        Number checkId = testHelper.addTestCheck();
        Number userId = testHelper.addTestUser(loginInfo);
        testHelper.addTestPhoto(checkId, userId);
        CheckCounters checkCounters = checkDao.getCheckCounters(checkId.intValue());
        assertNotNull(checkCounters);
        assertEquals(checkCounters.getPlayersCount(), 1);
    }

    @Rollback
    public void testGetCurrentCheck() {
        testHelper.addTestCheck();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(checkDao.getCurrentCheck());
    }

    @Rollback
    public void testGetVoteCheck() {
        testHelper.addVoteCheck(3);
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(checkDao.getVoteCheck());
    }

    @Rollback
    public void testGetVoteChecks() {
        testHelper.addVoteCheck(3);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -3);
        checkDao.addNewCheck(new CheckDto("test2", "set", calendar.getTime(), 3, 10));
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(checkDao.getVoteChecks().size(), 2);
    }

    @Rollback
    public void testIsVoteGoing() {
        Number checkId = testHelper.addVoteCheck(4);
        assertTrue(checkDao.isVoteGoing(checkId.intValue()));
    }

    @Rollback
    public void testIsCheckActive() {
        Number checkId = testHelper.addVoteCheck(1);
        assertTrue(checkDao.isCheckActive(checkId.intValue()));
    }
}
