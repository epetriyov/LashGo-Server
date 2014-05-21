package main.java.com.lashgo.service;

import com.lashgo.model.dto.ContentDto;
import main.java.com.lashgo.domain.Sessions;
import main.java.com.lashgo.domain.Users;
import main.java.com.lashgo.repository.SessionDao;
import main.java.com.lashgo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public List<ContentDto> getNews(String sessionId) {
        Sessions sessions = sessionDao.getSessionById(sessionId);
        Users users = userDao.getUserById(sessions.getUserId());
        return new ArrayList<>();
    }
}
