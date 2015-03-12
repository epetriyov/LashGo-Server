package com.lashgo.test.components;

import com.lashgo.domain.Photos;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.repository.ApnDao;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.PhotoDao;
import com.lashgo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

import static org.testng.Assert.*;

/**
 * Created by Eugene on 11.03.2015.
 */
@Component
public class TestHelper {

    @Autowired
    private ApnDao apnDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private PhotoDao photoDao;

    public void addApns(LoginInfo loginInfo, String token) {
        addTestUser(loginInfo);
        apnDao.addApnsToken(token, userDao.findUser(loginInfo).getId());
        assertTrue(apnDao.isApnsTokenExists(token));
    }

    public Number addTestCheck() {
        Number checkId = checkDao.addNewCheck(new CheckDto("test", "set", Calendar.getInstance().getTime(), 48, 10));
        assertNotNull(checkDao.getCheckById(checkId.intValue()));
        return checkId;
    }

    public Number addVoteCheck(int hoursAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -hoursAgo);
        Number checkId = checkDao.addNewCheck(new CheckDto("test", "set", calendar.getTime(), 3, 10));
        assertNotNull(checkDao.getCheckById(checkId.intValue()));
        return checkId;
    }

    public void assertCheckDto(CheckDto checkDto, int userId, long photoId) {
        assertEquals(checkDto.getPlayersCount(), 1);
        assertTrue(checkDto.getUserPhotoDto() != null);
        assertEquals(checkDto.getUserPhotoDto().getUrl(), "url");
        assertTrue(checkDto.getWinnerInfo() != null);
        assertEquals(checkDto.getWinnerInfo().getId(), userId);
        assertTrue(checkDto.getWinnerPhotoDto() != null);
        assertEquals(checkDto.getWinnerPhotoDto().getUrl(), "url");
        assertEquals(checkDto.getWinnerPhotoDto().getId(), photoId);
    }

    public Number addTestUser(LoginInfo loginInfo) {
        Number userId = userDao.createUser(loginInfo);
        assertNotNull(userDao.findUser(loginInfo));
        return userId;
    }

    public Number addTestPhoto(Number checkId, Number userId) {
        Number photoId = photoDao.savePhoto(new Photos("url", userId.intValue(), checkId.intValue()));
        assertNotNull(photoDao.getPhotoById(photoId.longValue()));
        return photoId;
    }
}
