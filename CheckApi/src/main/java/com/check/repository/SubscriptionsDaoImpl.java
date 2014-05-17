package main.java.com.check.repository;

import com.check.model.dto.SubscriptionDto;
import main.java.com.check.mappers.SubscriptionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public void removeSubscription(int userId, int checkistId) {
        jdbcTemplate.update("DELETE FROM subscriptions WHERE user_id = ? AND checklist_id = ?", userId, checkistId);
    }

    @Override
    public void addSubscription(int userId, int checkistId) {
        jdbcTemplate.update("INSERT INTO subscriptions (user_id,checklist_id) VALUES (?,?)", userId, checkistId);
    }
}
