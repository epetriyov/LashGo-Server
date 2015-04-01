package com.lashgo.dao;

import com.lashgo.dao.components.TestHelper;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.CheckWinnersDao;
import com.lashgo.repository.UserVotesDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Eugene on 15.03.2015.
 */
@Test(groups = "checkWinnersDao")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CheckWinnersDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CheckWinnersDao checkWinnersDao;

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private UserVotesDao userVotesDao;

    private LoginInfo loginInfo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public void initData() {
        loginInfo = new LoginInfo("test", CheckUtils.md5("test"));
    }

    @DataProvider(name = "winnersTestData")
    public Object[][] createWinnersTestData() {
        return new Object[][]{
                {new LoginInfo[]{}, new String[]{}, new int[]{}, -1},
                {new LoginInfo[]{
                        new LoginInfo("test1", CheckUtils.md5("test1")),
                        new LoginInfo("test2", CheckUtils.md5("test2")),
                        new LoginInfo("test3", CheckUtils.md5("test3"))},
                        new String[]{"url1", "url2", "url3"},
                        new int[]{0, 0, 0}, 0},
                {new LoginInfo[]{
                        new LoginInfo("test1", CheckUtils.md5("test1")),
                        new LoginInfo("test2", CheckUtils.md5("test2")),
                        new LoginInfo("test3", CheckUtils.md5("test3"))},
                        new String[]{"url1", "url2", "url3"},
                        new int[]{3, 3, 3,}, 0},
                {new LoginInfo[]{
                        new LoginInfo("test1", CheckUtils.md5("test1")),
                        new LoginInfo("test2", CheckUtils.md5("test2")),
                        new LoginInfo("test3", CheckUtils.md5("test3"))},
                        new String[]{"url1", "url2", "url3"},
                        new int[]{1, 2, 3,}, 2}
        };
    }


    @DataProvider(name = "addTestData")
    public Object[][] createAddTestData() {
        return new Object[][]{
                {new LoginInfo[]{
                        new LoginInfo("test1", CheckUtils.md5("test1")),
                        new LoginInfo("test2", CheckUtils.md5("test2")),
                        new LoginInfo("test3", CheckUtils.md5("test3"))},
                        new String[]{"url1", "url2", "url3"},
                        new int[]{3, 3, 3,}, 0}
        };
    }

    @Rollback
    @Test(dataProvider = "winnersTestData")
    public void testAddCheckWinner(LoginInfo[] participants, String[] photoUrls, int[] votesCountArray, int expectedWinnerNumber) {
        assertNotNull(participants);
        assertNotNull(votesCountArray);
        assertNotNull(photoUrls);
        assertEquals(participants.length, votesCountArray.length);
        assertEquals(participants.length, photoUrls.length);
        assertTrue(participants.length > expectedWinnerNumber);
        for (int l = 0; l < votesCountArray.length; l++) {
            assertTrue(participants.length >= votesCountArray[l]);
        }
        Number checkId = testHelper.addTestCheck();
        List<Number> usersList = new ArrayList<>(participants.length);
        for (int i = 0; i < participants.length; i++) {
            Number userId = testHelper.addTestUser(participants[i]);
            usersList.add(userId);
        }
        for (int i = 0; i < participants.length; i++) {
            Number photoId = testHelper.addTestPhoto(photoUrls[i], checkId, usersList.get(i));
            for (int j = 0; j < votesCountArray[i]; j++) {
                userVotesDao.addUserVote(usersList.get(j).intValue(), photoId.longValue());
            }
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        checkWinnersDao.addCheckWinner(checkId.intValue());
        int actualWinnerId = checkWinnersDao.getCheckWinner(checkId.intValue());
        if (expectedWinnerNumber == -1) {
            assertEquals(actualWinnerId, 0);
        } else {
            assertEquals(actualWinnerId, usersList.get(expectedWinnerNumber).intValue());
        }
    }

    @Rollback
    public void testGetCheckWinner() {
        Number checkId = testHelper.addTestCheck();
        Number expectedUserId = testHelper.addTestUser(loginInfo);
        Number actualUserId = checkWinnersDao.getCheckWinner(checkId.intValue());
        assertEquals(actualUserId, 0);
        jdbcTemplate.update("INSERT INTO check_winners (check_id,winner_id) VALUES (?,?)", checkId.intValue(), expectedUserId.intValue());
        actualUserId = checkWinnersDao.getCheckWinner(checkId.intValue());
        assertEquals(actualUserId, expectedUserId);
    }
}
