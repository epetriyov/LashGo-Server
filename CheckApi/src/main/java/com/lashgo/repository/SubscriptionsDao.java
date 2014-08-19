package com.lashgo.repository;

import com.lashgo.model.dto.SubscriptionDto;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 05.05.2014.
 */
public interface SubscriptionsDao {
    List<SubscriptionDto> getSubscriptions(int userId);

    void removeSubscription(int userId, int checkistId);

    void addSubscription(int usersId, int checkistId);

    int getNewerSubscriptions(int userId, Date lastView);

    int getSubscriptionsCount(int usersId);

    int getSubscribersCount(int usersId);
}
