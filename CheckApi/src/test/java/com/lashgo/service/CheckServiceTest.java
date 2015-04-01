package com.lashgo.service;

import com.lashgo.dao.components.TestHelper;
import com.lashgo.mappers.CheckDtoMapper;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.CheckWinnersDao;
import com.lashgo.repository.PhotoDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.assertEquals;

/**
 * Created by Eugene on 15.03.2015.
 */
@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CheckServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CheckService checkService;

    @Autowired
    private TestHelper testHelper;

    private LoginInfo loginInfo;

    @Autowired
    private CheckWinnersDao checkWinnersDao;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private MessageSource messageSource;

    @BeforeClass
    public void initData() {
        loginInfo = new LoginInfo("test", CheckUtils.md5("test"));
    }

    @DataProvider(name = "getChooseWinner")
    public Object[][] getChooseWinnerData() {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.add(Calendar.HOUR, -2);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.add(Calendar.MINUTE, -1);
        secondCalendar.add(Calendar.HOUR, -3);
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.add(Calendar.MINUTE, -2);
        thirdCalendar.add(Calendar.HOUR, -3);
        Calendar fourthCalendar = Calendar.getInstance();
        fourthCalendar.add(Calendar.SECOND, -1);
        fourthCalendar.add(Calendar.HOUR, -3);
        return new Object[][]{
                {new CheckDto[]{new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", firstCalendar.getTime(), 2, 1)}, new String[]{"url"}, new boolean[]{false}},
                {new CheckDto[]{new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", secondCalendar.getTime(), 2, 1)}, new String[]{"url"}, new boolean[]{false}},
                {new CheckDto[]{new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", thirdCalendar.getTime(), 2, 1)}, new String[]{"url"}, new boolean[]{false}},
                {new CheckDto[]{new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", fourthCalendar.getTime(), 2, 1)}, new String[]{"url"}, new boolean[]{true}},
                {new CheckDto[]{new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", fourthCalendar.getTime(), 2, 1), new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", thirdCalendar.getTime(), 2, 1)}, new String[]{"url1", "url2"}, new boolean[]{true, false}},
                {new CheckDto[]{new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", fourthCalendar.getTime(), 2, 1), new CheckDto(messageSource.getMessage("check.name", null, Locale.US), "set", fourthCalendar.getTime(), 2, 1)}, new String[]{"url1", "url2"}, new boolean[]{true, true}},
        };
    }


    @Rollback
    @Test(dataProvider = "getChooseWinner")
    public void testChooseWinner(CheckDto[] checkDtos, String[] photoUrls, boolean[] winners) {
        Number userId = testHelper.addTestUser(loginInfo);
        List<Integer> checkIds = new ArrayList<>(checkDtos.length);
        for (int i = 0; i < checkDtos.length; i++) {
            Number checkId = testHelper.addTestCheck(checkDtos[i]);
            testHelper.addTestPhoto(photoUrls[i], checkId.intValue(), userId);
            checkIds.add(checkId.intValue());
        }
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkService.chooseWinner();
        for (int i = 0; i < checkDtos.length; i++) {
            assertEquals(checkWinnersDao.getCheckWinner(checkIds.get(i)) == userId.intValue(), winners[i]);
            CheckDto checkDto = null;
            try {
                checkDto = jdbcTemplate.queryForObject("SELECT * FROM checks WHERE task_photo = ?", new CheckDtoMapper(), photoDao.getPhoto(checkIds.get(i), userId.intValue()).getUrl());
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
            assertEquals(checkDto != null, winners[i]);
        }
    }

}
