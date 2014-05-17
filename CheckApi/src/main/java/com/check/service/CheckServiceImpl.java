package main.java.com.check.service;

import com.check.model.dto.CheckDto;
import com.check.model.dto.CheckDtoList;
import com.check.model.dto.PhotoDtoList;
import main.java.com.check.domain.Check;
import main.java.com.check.domain.Sessions;
import main.java.com.check.domain.Users;
import main.java.com.check.repository.CheckDao;
import main.java.com.check.repository.CommentDao;
import main.java.com.check.repository.PhotoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@Service
@Transactional(readOnly = true)
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private PhotoDao photoDao;

    @Override
    public CheckDtoList getChecks() {
        List<Check> checkList = checkDao.getAllChecks();
        CheckDtoList checkDtoList = new CheckDtoList();
        List<CheckDto> checkDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(checkList)) {
            for (Check check : checkList) {
                checkDtos.add(new CheckDto(check.getId(), check.getName(), check.getDescription(), check.getStartDate(), check.getDuration(), check.getPhoto()));
            }
        }
        checkDtoList.setChecks(checkDtos);
        return checkDtoList;
    }

    @Override
    public CheckDto getCurrentCheck() {
        Check check = checkDao.getNextCheck();
        if (check != null) {
            return new CheckDto(check.getId(), check.getName(), check.getDescription(), check.getStartDate(), check.getDuration(), check.getPhoto());
        }
        return null;
    }

    @Override
    public PhotoDtoList getPhotos(long checkId) {
        return new PhotoDtoList(photoDao.getPhotosByCheckId(checkId));
    }

}
