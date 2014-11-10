package com.lashgo.service;

import com.lashgo.model.dto.EventDto;

import java.util.List;

/**
 * Created by Eugene on 19.10.2014.
 */
public interface EventService {
    List<EventDto> getEvents(String sessionId, boolean subscribesEvents);
}
