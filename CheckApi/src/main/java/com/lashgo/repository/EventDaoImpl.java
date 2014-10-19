package com.lashgo.repository;

import com.lashgo.mappers.EventMapper;
import com.lashgo.model.DbCodes;
import com.lashgo.model.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eugene on 19.10.2014.
 */
@Repository
public class EventDaoImpl implements EventDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addCommentEvent(int userId, long photoId) {
        jdbcTemplate.update("INSERT INTO events (user_id,action,photo_id) VALUES (?,?,?)", userId, DbCodes.EventActions.COMMENT.name(), photoId);
    }

    @Override
    public void addWinEvent(int checkId, int userId) {
        jdbcTemplate.update("INSERT INTO events (user_id, action, check_id) VALUES (?,?,?)", userId, DbCodes.EventActions.WIN.name(), checkId);
    }

    @Override
    public void addSibscribeEvent(int userId, int subscriptionId) {
        jdbcTemplate.update("INSERT INTO events (user_id, action, object_user_id) VALUES (?,?,?)", userId, DbCodes.EventActions.SUBSCRIBE.name(), subscriptionId);
    }

    @Override
    public List<EventDto> getEventsByUser(int userId) {
        return jdbcTemplate.query("SELECT e.id, u1.id as uid1, u1.login as ulogin1, u1.fio as ufio1,u1.avatar as uavatar1, " +
                "                  p.id as pid, p.picture as picture, c.id as cid, c.name as cname,c.task_photo as ctask_photo, " +
                "                  u2.id as uid2,u2.login as ulogin2,u2.fio as ufio2,u2.avatar as uavatar2,e.action as action,e.event_date as event_date" +
                "             FROM events e LEFT JOIN users u1 ON (u1.id = e.user_id)" +
                "             LEFT JOIN photos p ON (p.id = e.photo_id) " +
                "             LEFT JOIN checks c ON (c.id = e.check_id) " +
                "             LEFT JOIN users u2 ON (u2.id = e.object_user_id)" +
                "            WHERE e.user_id = ? OR e.object_user_id = ?" +
                "               OR e.user_id IN (SELECT checklist_id FROM subscriptions WHERE user_id = ?)" +
                "               OR e.object_user_id IN (SELECT checklist_id FROM subscriptions WHERE user_id = ?)", new EventMapper(), userId, userId, userId, userId);
    }
}
