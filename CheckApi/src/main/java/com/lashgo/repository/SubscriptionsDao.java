package com.lashgo.repository;

import com.lashgo.model.dto.SubscriptionDto;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 05.05.2014.
 */
public interface SubscriptionsDao {
    List<SubscriptionDto> getSubscribers(int userId, int currentUser);

    List<SubscriptionDto> getSubscriptions(int userId, int currentUser);

    void removeSubscription(int userId, int checkistId);

    void addSubscription(int usersId, int checkistId);

    int getSubscriptionsCount(int usersId);

    int getSubscribersCount(int usersId);

    boolean isSubscriptionExists(int userId, int checklistId);
}
