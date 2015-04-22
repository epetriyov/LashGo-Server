package com.lashgo.dao;

import com.lashgo.dao.components.TestHelper;
import com.lashgo.domain.Check;
import com.lashgo.model.CheckType;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.CheckWinnersDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Eugene on 11.03.2015.
 */
@Test(groups = "checkDao")
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

    @DataProvider(name = "getAllChecksData")
    public Object[][] createGetAllChecksData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        return new Object[][]{
                {new CheckDto[]{}, null, null, false, false, 0},
                {new CheckDto[]{}, "test", null, false, false, 0},
                {new CheckDto[]{new CheckDto("test", "set", Calendar.getInstance().getTime(), 48, 10)}, "test", null, false, false, 1},
                {new CheckDto[]{new CheckDto("test", "set", Calendar.getInstance().getTime(), 48, 10)}, "set", null, false, false, 1},
                {new CheckDto[]{new CheckDto("test", "set", Calendar.getInstance().getTime(), 48, 10)}, "", null, false, false, 1},
                {new CheckDto[]{new CheckDto("test", "set", Calendar.getInstance().getTime(), 48, 10)}, null, loginInfo, true, true, 1},
                {new CheckDto[]{new CheckDto("test", "set", calendar.getTime(), 48, 10)}, null, loginInfo, true, true, 0},
        };
    }

    @DataProvider(name = "getCurrentCheckData")
    public Object[][] createGetCurrentCheckData() {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.add(Calendar.SECOND, -59);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.add(Calendar.SECOND, -60);
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.add(Calendar.SECOND, 5);
        return new Object[][]{
                {new CheckDto[]{}, null},
                {new CheckDto[]{new CheckDto("test", "set", Calendar.getInstance().getTime(), 48, 10)}, "test"},
                {new CheckDto[]{new CheckDto("test", "set", firstCalendar.getTime(), 48, 10)}, "test"},
                {new CheckDto[]{new CheckDto("test", "set", secondCalendar.getTime(), 48, 10)}, null},
                {new CheckDto[]{new CheckDto("test", "set", thirdCalendar.getTime(), 48, 10)}, null},
        };
    }

    @DataProvider(name = "getVoteCheckData")
    public Object[][] createGetVoteCheckData() {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.add(Calendar.SECOND, -59);
        firstCalendar.add(Calendar.HOUR, -3);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.add(Calendar.SECOND, -60);
        secondCalendar.add(Calendar.HOUR, -3);
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.add(Calendar.HOUR, -3);
        return new Object[][]{
                {new CheckDto[]{}, null},
                {new CheckDto[]{new CheckDto("test", "set", firstCalendar.getTime(), 3, 10)}, "test"},
                {new CheckDto[]{new CheckDto("test", "set", secondCalendar.getTime(), 3, 10)}, null},
                {new CheckDto[]{new CheckDto("test", "set", thirdCalendar.getTime(), 3, 10)}, "test"},
        };
    }

    @DataProvider(name = "isCheckActiveData")
    public Object[][] createIsCheckActiveData() {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.add(Calendar.SECOND, -59);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.add(Calendar.SECOND, -60);
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.add(Calendar.SECOND, 1);
        return new Object[][]{
                {new CheckDto("test", "set", Calendar.getInstance().getTime(), 3, 10), true},
                {new CheckDto("test", "set", firstCalendar.getTime(), 3, 10), true},
                {new CheckDto("test", "set", secondCalendar.getTime(), 3, 10), true},
                {new CheckDto("test", "set", thirdCalendar.getTime(), 3, 10), false},
        };
    }

    @DataProvider(name = "isVoteGoingData")
    public Object[][] createIsVoteGoingData() {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.add(Calendar.SECOND, -59);
        firstCalendar.add(Calendar.HOUR, -3);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.add(Calendar.SECOND, -60);
        secondCalendar.add(Calendar.HOUR, -3);
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.add(Calendar.HOUR, -3);
        return new Object[][]{
                {new CheckDto("test", "set", firstCalendar.getTime(), 3, 10), true},
                {new CheckDto("test", "set", secondCalendar.getTime(), 3, 10), true},
                {new CheckDto("test", "set", thirdCalendar.getTime(), 3, 10), true},
        };
    }

    @DataProvider(name = "getVoteChecksData")
    public Object[][] createGetVoteChecksData() {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.add(Calendar.SECOND, -59);
        firstCalendar.add(Calendar.HOUR, -3);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.add(Calendar.SECOND, -60);
        secondCalendar.add(Calendar.HOUR, -3);
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.add(Calendar.HOUR, -3);
        return new Object[][]{
                {new CheckDto[]{}, new int[]{}},
                {new CheckDto[]{new CheckDto("test", "set", firstCalendar.getTime(), 2, 1)}, new int[]{0}},
                {new CheckDto[]{new CheckDto("test", "set", secondCalendar.getTime(), 2, 1)}, new int[]{}},
                {new CheckDto[]{new CheckDto("test", "set", thirdCalendar.getTime(), 2, 1)}, new int[]{0}},
        };
    }

    @Rollback
    @Test(dataProvider = "getAllChecksData")
    public void testGetAllChecksCount(CheckDto[] checkDtos, String queryText, LoginInfo loginInfo, boolean addPhoto, boolean chooseWinner, int expectedCheckCount) {
        assertNotNull(checkDtos);
        List<Number> checkList = new ArrayList<>(checkDtos.length);
        Number userId = null;
        if (loginInfo != null) {
            userId = testHelper.addTestUser(loginInfo);
        }
        for (CheckDto checkDto : checkDtos) {
            Number checkId = testHelper.addTestCheck(checkDto);
            checkList.add(checkId);
            if (addPhoto && userId != null) {
                testHelper.addTestPhoto(checkId, userId);
            }
            if (chooseWinner) {
                checkWinnersDao.addCheckWinner(checkId.intValue());
            }
        }
        assertEquals(checkDao.getAllStartedChecks(userId == null ? -1 : userId.intValue(), queryText, null).size(), expectedCheckCount);

    }

    @Rollback
    public void testGetAllChecksCountResult() {
        CheckDto expectedCheckDto = new CheckDto("test", "test", Calendar.getInstance().getTime(), 3, 4);
        expectedCheckDto.setPlayersCount(1);
        String expectedPhotoUrl = "url";
        Number userId = testHelper.addTestUser(loginInfo);
        Number checkId = testHelper.addTestCheck(expectedCheckDto);
        Number photoId = testHelper.addTestPhoto(expectedPhotoUrl, checkId, userId);
        checkWinnersDao.addCheckWinner(checkId.intValue());
        List<CheckDto> checkDtoList = checkDao.getAllStartedChecks(userId.intValue(), null, null);
        assertNotNull(checkDtoList);
        assertEquals(checkDtoList.size(), 1);
        CheckDto actualCheckDto = checkDtoList.iterator().next();
        testHelper.assertCheckDto(actualCheckDto, expectedCheckDto, userId.intValue(), photoId.longValue(), expectedPhotoUrl);
    }

    @Rollback
    public void testAddNewCheck() {
        Date startDate = Calendar.getInstance().getTime();
        Number checkId = checkDao.addNewCheck(new CheckDto("test", "set", startDate, 48, 10, "task",CheckType.SELFIE.name()));
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
    public void testGetActiveSelfieCount() {
        testHelper.addTestCheck(CheckType.SELFIE);
        assertEquals(checkDao.getActiveChecksCount(CheckType.SELFIE), 1);
    }

    @Rollback
    public void testGetActiveActionsCount() {
        testHelper.addTestCheck(CheckType.ACTION);
        assertEquals(checkDao.getActiveChecksCount(CheckType.ACTION), 1);
    }

    @Rollback
    public void testGetCheckById() {
        CheckDto expectedCheckDto = new CheckDto("test", "test", Calendar.getInstance().getTime(), 3, 4);
        expectedCheckDto.setPlayersCount(1);
        String expectedPhotoUrl = "url";
        Number checkId = testHelper.addTestCheck(expectedCheckDto);
        Number userId = testHelper.addTestUser(loginInfo);
        Number photoId = testHelper.addTestPhoto(checkId, userId);
        checkWinnersDao.addCheckWinner(checkId.intValue());
        CheckDto checkDto = checkDao.getCheckById(userId.intValue(), checkId.intValue());
        assertNotNull(checkDto);
        testHelper.assertCheckDto(checkDto, expectedCheckDto, userId.intValue(), photoId.longValue(), expectedPhotoUrl);
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
    @Test(dataProvider = "getCurrentCheckData")
    public void testGetCurrentCheck(CheckDto[] checks, String currentCheckName) {
        assertNotNull(checks);
        for (CheckDto checkDto : checks) {
            testHelper.addTestCheck(checkDto);
        }
        Check checkDto = checkDao.getCurrentCheck();
        assertEquals(checkDto != null ? checkDto.getName() : null, currentCheckName);
    }

    @Rollback
    @Test(dataProvider = "getVoteCheckData")
    public void testGetVoteCheck(CheckDto[] checks, String currentCheckName) {
        assertNotNull(checks);
        for (CheckDto checkDto : checks) {
            testHelper.addTestCheck(checkDto);
        }
        Check checkDto = checkDao.getVoteCheck();
        assertEquals(checkDto != null ? checkDto.getName() : null, currentCheckName);
    }

    @Rollback
    @Test(dataProvider = "getVoteChecksData")
    public void testGetVoteChecks(CheckDto[] checks, int[] voteCheckPositions) {
        assertNotNull(checks);
        assertNotNull(voteCheckPositions);
        List<Integer> expectedCheckIds = new ArrayList<>();
        for (int i = 0; i < checks.length; i++) {
            for (int j = 0; j < voteCheckPositions.length; j++) {
                if (i == voteCheckPositions[j]) {
                    expectedCheckIds.add(testHelper.addTestCheck(checks[i]).intValue());
                }
            }
        }
        List<Integer> actualCheckIds = checkDao.getFinishedChecks();
        assertEquals(actualCheckIds, expectedCheckIds);
    }

    @Rollback
    @Test(dataProvider = "isVoteGoingData")
    public void testIsVoteGoing(CheckDto checkDto, boolean isVoteGoing) {
        Number checkId = testHelper.addTestCheck(checkDto);
        assertEquals(checkDao.isVoteGoing(checkId.intValue()), isVoteGoing);
    }

    @Rollback
    @Test(dataProvider = "isCheckActiveData")
    public void testIsCheckActive(CheckDto checkDto, boolean isCheckActive) {
        assertNotNull(checkDto);
        Number checkId = testHelper.addTestCheck(checkDto);
        assertEquals(checkDao.isCheckActive(checkId.intValue()), isCheckActive);
    }
}
