package com.lashgo.dao;

import com.lashgo.dao.components.TestHelper;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.UserVotesDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Eugene on 15.03.2015.
 */
@Test(groups = {"userVotesDao"})
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserVotesDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserVotesDao userVotesDao;

    @Autowired
    private TestHelper testHelper;

    @Rollback
    public void testAddUserVote() {
        Number checkId = testHelper.addTestCheck();
        Number userId = testHelper.addTestUser(new LoginInfo("test", CheckUtils.md5("test")));
        Number photoId = testHelper.addTestPhoto(checkId, userId);
        userVotesDao.addUserVote(userId.intValue(), photoId.longValue());
        Integer actuaUserVotesCount = jdbcTemplate.queryForObject("select count(*) from user_votes where user_id = ? and photo_id = ?",Integer.class,userId.intValue(),photoId.longValue());
        assertEquals(actuaUserVotesCount.intValue(),1);
    }
}
