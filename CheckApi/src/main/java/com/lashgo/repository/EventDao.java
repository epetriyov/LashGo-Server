package com.lashgo.repository;

import com.lashgo.model.dto.EventDto;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 19.10.2014.
 */
public interface EventDao {

    void addCommentEvent(int userId, long photoId);

    void addWinEvent(int checkId,int userId);

    void addSibscribeEvent(int userId, int subscriptionId);

    List<EventDto> getEventsByUser(int userId, boolean subscribesEvents);

    int getEventsCountByUser(int userId, Date lastView);

    void addVoteEvent(int userId, long photoId);

    void addCheckParticipateEvent(int userId, int checkId);
}
