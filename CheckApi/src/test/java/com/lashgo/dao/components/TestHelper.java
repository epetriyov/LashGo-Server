package com.lashgo.dao.components;

import com.lashgo.domain.Photos;
import com.lashgo.model.CheckType;
import com.lashgo.model.dto.CheckDto;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.PhotoDto;
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

    public Number addTestCheck(CheckType checkType) {
        Number checkId = checkDao.addNewCheck(new CheckDto("test", "set", Calendar.getInstance().getTime(), 48, 10, checkType));
        assertNotNull(checkDao.getCheckById(checkId.intValue()));
        return checkId;
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

    public void assertCheckDto(CheckDto actualCheckDto, CheckDto expectedCheckDto, int userId, long photoId, String expectedPhotoUrl) {
        assertEquals(actualCheckDto.getDescription(), expectedCheckDto.getDescription());
        assertEquals(actualCheckDto.getDuration(), expectedCheckDto.getDuration());
        assertEquals(actualCheckDto.getName(), expectedCheckDto.getName());
        assertEquals(actualCheckDto.getPlayersCount(), expectedCheckDto.getPlayersCount());
        assertEquals(actualCheckDto.getStartDate(), expectedCheckDto.getStartDate());
        assertEquals(actualCheckDto.getTaskPhotoUrl(), expectedCheckDto.getTaskPhotoUrl());
        assertEquals(actualCheckDto.getVoteDuration(), expectedCheckDto.getVoteDuration());
        assertEquals(actualCheckDto.getPlayersCount(), 1);
        assertTrue(actualCheckDto.getUserPhotoDto() != null);
        assertEquals(actualCheckDto.getUserPhotoDto().getUrl(), expectedPhotoUrl);
        assertTrue(actualCheckDto.getWinnerInfo() != null);
        assertEquals(actualCheckDto.getWinnerInfo().getId(), userId);
        assertTrue(actualCheckDto.getWinnerPhotoDto() != null);
        assertEquals(actualCheckDto.getWinnerPhotoDto().getUrl(), expectedPhotoUrl);
        assertEquals(actualCheckDto.getWinnerPhotoDto().getId(), photoId);
    }

    public Number addTestUser(LoginInfo loginInfo) {
        Number userId = userDao.createUser(loginInfo);
        assertNotNull(userDao.findUser(loginInfo));
        return userId;
    }

    public Number addTestPhoto(Number checkId, Number userId) {
        return addTestPhoto("url", checkId, userId);
    }

    public Number addTestPhoto(String photoUrl, Number checkId, Number userId) {
        Number photoId = photoDao.savePhoto(new Photos(photoUrl, userId.intValue(), checkId.intValue()));
        assertNotNull(photoDao.getPhotoById(photoId.longValue()));
        return photoId;
    }

    public Number addTestCheck(CheckDto checkDto) {
        Number checkId = checkDao.addNewCheck(checkDto);
        assertNotNull(checkDao.getCheckById(checkId.intValue()));
        return checkId;
    }

    public void assertPhotoDto(PhotoDto actualPhotoDto, PhotoDto expectedPhotoDto, boolean check, boolean user) {
        assertNotNull(actualPhotoDto);
        assertEquals(actualPhotoDto.getId(), expectedPhotoDto.getId());
        assertEquals(actualPhotoDto.getUrl(), expectedPhotoDto.getUrl());
        if (user) {
            assertNotNull(actualPhotoDto.getUser());
            assertEquals(actualPhotoDto.getUser().getId(), expectedPhotoDto.getUser().getId());
        }
        assertEquals(actualPhotoDto.getLikesCount(), expectedPhotoDto.getLikesCount());
        assertEquals(actualPhotoDto.getCommentsCount(), expectedPhotoDto.getCommentsCount());
        if (check) {
            assertNotNull(actualPhotoDto.getCheck());
            assertEquals(actualPhotoDto.getCheck().getId(), expectedPhotoDto.getCheck().getId());
        }
    }
}
