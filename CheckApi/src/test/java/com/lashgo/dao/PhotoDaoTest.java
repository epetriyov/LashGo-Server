package com.lashgo.dao;

import com.lashgo.domain.Photos;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.repository.PhotoDao;
import com.lashgo.dao.components.TestHelper;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

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

    @Rollback
    public void testSavePhoto() {
        Number userId = testHelper.addTestUser(new LoginInfo("test", CheckUtils.md5("test")));
        Number checkId = testHelper.addTestCheck();
        Number photoId = photoDao.savePhoto(new Photos("picture", userId.intValue(), checkId.intValue()));
        PhotoDto photoDto = photoDao.getJustPhotoById(photoId.longValue());
        assertNotNull(photoDto);
        assertEquals(photoDto.getUrl(), "picture");
        assertNotNull(photoDto.getCheck());
        assertNotNull(photoDto.getUser());
        assertEquals(photoDto.getCheck().getId(), checkId.intValue());
        assertEquals(photoDto.getUser().getId(), userId.intValue());
    }
}
