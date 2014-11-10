package com.lashgo.service;

import com.lashgo.domain.Users;
import com.lashgo.model.dto.EventDto;
import com.lashgo.repository.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Eugene on 19.10.2014.
 */
@Transactional(readOnly = true)
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserService userService;

    @Override
    public List<EventDto> getEvents(String sessionId, boolean subscribesEvents) {
        Users users = userService.getUserBySession(sessionId);
        return eventDao.getEventsByUser(users.getId(),subscribesEvents);
    }
}
