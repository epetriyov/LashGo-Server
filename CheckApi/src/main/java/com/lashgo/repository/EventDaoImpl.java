package com.lashgo.repository;

import com.lashgo.mappers.EventMapper;
import com.lashgo.model.DbCodes;
import com.lashgo.model.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Date;
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
    public List<EventDto> getEventsByUser(int userId, boolean subscribesEvents) {
        return jdbcTemplate.query("SELECT e.id as id, u1.id as uid1, u1.login as ulogin1, u1.fio as ufio1,u1.avatar as uavatar1, " +
                "                  p.id as pid, p.picture as picture, c.id as cid, c.name as cname,c.task_photo as ctask_photo, " +
                "                  u2.id as uid2,u2.login as ulogin2,u2.fio as ufio2,u2.avatar as uavatar2,e.action as action,e.event_date as event_date" +
                "             FROM events e LEFT JOIN users u1 ON (u1.id = e.user_id)" +
                "             LEFT JOIN photos p ON (p.id = e.photo_id) " +
                "             LEFT JOIN checks c ON (c.id = e.check_id) " +
                "             LEFT JOIN users u2 ON (u2.id = e.object_user_id)" +
                "            WHERE " +
                (!subscribesEvents ? "e.action != ?" : "e.action = ?") + " AND (e.object_user_id = ?" +
                "               OR e.action = ?" +
                "               OR e.photo_id IN (SELECT ph.id FROM photos ph WHERE ph.user_id = ?)" +
                "               OR (e.user_id IN (SELECT checklist_id FROM subscriptions WHERE user_id = ?)" +
                "              AND e.action = ?)) ORDER BY event_date desc", new EventMapper(), DbCodes.EventActions.SUBSCRIBE.name(), userId, DbCodes.EventActions.WIN.name(), userId, userId, DbCodes.EventActions.CHECK.name());
    }

    @Override
    public int getEventsCountByUser(int userId, Date lastView) {
        return jdbcTemplate.queryForObject("SELECT count(e.id)" +
                        "             FROM events e" +
                        "            WHERE e.action = ? AND (e.object_user_id = ?" +
                        "               OR e.action = ?" +
                        "               OR e.photo_id IN (SELECT ph.id FROM photos ph WHERE ph.user_id = ?)" +
                        "               OR (e.user_id IN (SELECT checklist_id FROM subscriptions WHERE user_id = ?)" +
                        "              AND e.action = ?))" +
                        (lastView != null ?
                                " AND e.event_date > ?" : ""), (lastView != null ? new Object[]{DbCodes.EventActions.SUBSCRIBE.name(), userId, DbCodes.EventActions.WIN.name(),userId, userId, lastView} : new Object[]{DbCodes.EventActions.SUBSCRIBE.name(), userId, DbCodes.EventActions.WIN.name(),userId, userId}),
                (lastView != null ? new int[]{Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER,Types.INTEGER, Types.TIMESTAMP} : new int[]{Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER,Types.INTEGER}), Integer.class);
    }

    @Override
    public void addVoteEvent(int userId, long photoId) {
        jdbcTemplate.update("INSERT INTO events (user_id, action, photo_id) VALUES (?,?,?)", userId, DbCodes.EventActions.VOTE.name(), photoId);
    }

    @Override
    public void addCheckParticipateEvent(int userId, int checkId) {
        jdbcTemplate.update("INSERT INTO events (user_id, action, check_id) VALUES (?,?,?)", userId, DbCodes.EventActions.CHECK.name(), checkId);
    }
}
