package com.lashgo.dao;

import com.lashgo.dao.components.TestHelper;
import com.lashgo.domain.Photos;
import com.lashgo.model.dto.*;
import com.lashgo.repository.CheckWinnersDao;
import com.lashgo.repository.CommentDao;
import com.lashgo.repository.PhotoDao;
import com.lashgo.repository.UserVotesDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Eugene on 13.03.2015.
 */
@Test(groups = {"photoDao"})
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PhotoDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private UserVotesDao userVotesDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CheckWinnersDao checkWinnersDao;

    @Rollback
    public void testSavePhoto() {
        assertFalse(photoDao.isPhotoExists(1, 1));
        Number userId = testHelper.addTestUser(new LoginInfo("test", CheckUtils.md5("test")));
        Number checkId = testHelper.addTestCheck();
        Number photoId = photoDao.savePhoto(new Photos("picture", userId.intValue(), checkId.intValue()));
        PhotoDto expectedPhotoDto = new PhotoDto(photoId.longValue());
        expectedPhotoDto.setCheck(new CheckDto(checkId.intValue()));
        expectedPhotoDto.setUrl("picture");
        expectedPhotoDto.setUser(new UserDto(userId.intValue()));
        PhotoDto photoDto = photoDao.getJustPhotoById(photoId.longValue());
        testHelper.assertPhotoDto(photoDto, expectedPhotoDto, true, true);
        assertTrue(photoDao.isPhotoExists(userId.intValue(), checkId.intValue()));
        photoDto = photoDao.getPhoto(checkId.intValue(), userId.intValue());
        testHelper.assertPhotoDto(photoDto, expectedPhotoDto, true, true);
        userVotesDao.addUserVote(userId.intValue(), photoId.longValue());
        commentDao.addPhotoComment(userId.intValue(), photoId.longValue(), "test", new Date());
        photoDto = photoDao.getPhotoById(photoId.longValue());
        expectedPhotoDto.setCommentsCount(1);
        expectedPhotoDto.setLikesCount(1);
        testHelper.assertPhotoDto(photoDto, expectedPhotoDto,false, true);
        List<PhotoDto> photoDtos = photoDao.getPhotosByCheckId(checkId.intValue());
        assertNotNull(photoDtos);
        assertEquals(photoDtos.size(), 1);
        testHelper.assertPhotoDto(photoDtos.iterator().next(), expectedPhotoDto,false, true);
        CheckCounters checkCounters = photoDao.getPhotoCounters(photoId.longValue());
        assertNotNull(checkCounters);
        assertEquals(checkCounters.getLikesCount(), 1);
        assertEquals(checkCounters.getCommentsCount(), 1);
        checkWinnersDao.addCheckWinner(checkId.intValue());
        photoDtos = photoDao.getPhotosByUserId(userId.intValue());
        assertNotNull(photoDtos);
        assertEquals(photoDtos.size(), 1);
        PhotoDto actualPhotoDto = photoDtos.iterator().next();
        testHelper.assertPhotoDto(actualPhotoDto, expectedPhotoDto, true,false);
        assertTrue(actualPhotoDto.isWinner());
    }
}
