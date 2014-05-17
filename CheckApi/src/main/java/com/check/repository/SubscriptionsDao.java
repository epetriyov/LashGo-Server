package main.java.com.check.repository;

import com.check.model.dto.SubscriptionDto;

import java.util.List;

/**
 * Created by Eugene on 05.05.2014.
 */
public interface SubscriptionsDao {
    List<SubscriptionDto> getSubscriptions(int userId);

    void removeSubscription(int userId, int checkistId);

    void addSubscription(int usersId, int checkistId);
}
