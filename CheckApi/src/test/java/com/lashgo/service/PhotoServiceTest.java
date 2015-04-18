package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.dao.components.TestHelper;
import com.lashgo.model.DbCodes;
import com.lashgo.model.dto.LoginInfo;
import com.lashgo.model.dto.PhotoDto;
import com.lashgo.repository.PhotoDao;
import com.lashgo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.testng.Assert.*;

/**
 * Created by Eugene on 11.04.2015.
 */
@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PhotoServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private TestHelper testHelper;

    private String photoName;

    @Rollback
    public void testSavePhoto() {
        Number userId = testHelper.addTestUser(new LoginInfo("test", "test"));
        Number checkId = testHelper.addTestCheck();
        try {
            photoService.savePhoto("test", new FileInputStream(CheckConstants.PHOTOS_FOLDER + "task_9.jpg"));
            photoName = CheckUtils.buildNewPhotoName(checkId.intValue(), userId.intValue());
            File file = new File(CheckConstants.PHOTOS_FOLDER + photoName);
            assertTrue(file.exists());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    @AfterMethod
//    public void deleteTempFile() {
//        File file = new File(CheckConstants.PHOTOS_FOLDER + photoName);
//        if (file.exists()) {
//            file.delete();
//        }
//    }

}
