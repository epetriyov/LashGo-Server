package com.lashgo.repository;

import com.lashgo.mappers.SubscriptionsMapper;
import com.lashgo.model.dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 05.05.2014.
 */
@Repository
public class SubscriptionsDaoImpl implements SubscriptionsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SubscriptionDto> getSubscriptions(int userId, int currentUser) {
        return jdbcTemplate.query("" +
                "SELECT s.id,u.id as uid,u.login,u.fio,u.avatar, " +
                "COUNT(sub.id) AS sub_count " +
                "FROM subscriptions s, users u " +
                "LEFT JOIN subscriptions sub " +
                "ON (sub.user_id = ? AND sub.checklist_id= u.id) " +
                "WHERE s.user_id = ? AND s.checklist_id = u.id GROUP BY s.id,u.id,u.login,u.fio,u.avatar ORDER BY s.id ASC", new SubscriptionsMapper(), currentUser,userId);
    }

    @Override
    public List<SubscriptionDto> getSubscribers(int userId, int currentUser) {
        return jdbcTemplate.query("" +
                "SELECT s.id,u.id as uid,u.login,u.fio,u.avatar, " +
                "COUNT(sub.id) AS sub_count " +
                "FROM subscriptions s, users u " +
                "LEFT JOIN subscriptions sub " +
                "ON (sub.user_id = ? AND sub.checklist_id = u.id) " +
                "WHERE s.checklist_id = ? AND s.user_id = u.id " +
                "GROUP BY s.id,u.id,u.login,u.fio,u.avatar ORDER BY s.id ASC", new SubscriptionsMapper(), currentUser, userId);
    }

    @Override
    public void removeSubscription(int userId, int checkistId) {
        jdbcTemplate.update("DELETE FROM subscriptions WHERE user_id = ? AND checklist_id = ?", userId, checkistId);
    }

    public boolean isSubscriptionExists(int userId, int checklistId) {
        Integer subscriptionsCount = jdbcTemplate.queryForObject("SELECT count(s.id) FROM subscriptions s WHERE s.user_id = ? AND s.checklist_id = ?", Integer.class, userId, checklistId);
        return subscriptionsCount > 0;
    }

    @Override
    public void addSubscription(int userId, int checkistId) {
        jdbcTemplate.update("INSERT INTO subscriptions (user_id,checklist_id) VALUES (?,?)", userId, checkistId);
    }

    @Override
    public int getSubscriptionsCount(int usersId) {
        return jdbcTemplate.queryForObject("SELECT count(s.id) FROM subscriptions s WHERE s.user_id = ?", new Object[]{usersId}, Integer.class);
    }

    @Override
    public int getSubscribersCount(int usersId) {
        return jdbcTemplate.queryForObject("SELECT count(s.id) FROM subscriptions s WHERE s.checklist_id = ?", new Object[]{usersId}, Integer.class);
    }
}
