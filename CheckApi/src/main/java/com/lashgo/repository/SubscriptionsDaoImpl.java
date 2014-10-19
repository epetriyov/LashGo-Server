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
    public List<SubscriptionDto> getSubscriptions(int userId) {
        return jdbcTemplate.query("SELECT s.id,s.user_id,u.login,u.avatar FROM subscriptions s, users u WHERE s.user_id = ? AND s.checklist_id = u.id ORDER BY s.id ASC", new SubscriptionsMapper(), userId);
    }

    @Override
    public List<SubscriptionDto> getSubscribers(int userId) {
        return jdbcTemplate.query("SELECT s.id,s.user_id,u.login,u.avatar FROM subscriptions s, users u WHERE s.checklist_id = ? AND s.user_id = u.id ORDER BY s.id ASC", new SubscriptionsMapper(), userId);
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
    public int getNewerSubscriptions(int userId, Date lastView) {
        return jdbcTemplate.queryForObject(
                "SELECT count(s.id) FROM subscriptions s " +
                        " WHERE s.user_id = ?" + (lastView != null ? " AND s.subscribe_date > ?" : ""),
                (lastView != null ? new Object[]{userId, lastView} : new Object[]{userId}),
                (lastView != null ? new int[]{Types.INTEGER, Types.TIMESTAMP} : new int[]{Types.INTEGER}), Integer.class);
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
