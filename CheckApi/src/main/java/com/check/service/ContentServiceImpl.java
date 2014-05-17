package main.java.com.check.service;

import com.check.model.dto.ContentDtoList;
import main.java.com.check.domain.Sessions;
import main.java.com.check.domain.Users;
import main.java.com.check.repository.ContentDao;
import main.java.com.check.repository.SessionDao;
import main.java.com.check.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Eugene on 06.05.2014.
 */
@Transactional(readOnly = true)
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    public ContentDtoList getNews(String sessionId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        Users users = userDao.getUserById(sessions.getUserId());
        return new ContentDtoList();
    }
}
