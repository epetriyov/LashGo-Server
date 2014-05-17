package com.check.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 05.05.2014.
 */
public class SubscriptionDtoList implements Serializable {
    private List<SubscriptionDto> subscriptions;

    public SubscriptionDtoList() {
    }

    public SubscriptionDtoList(List<SubscriptionDto> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<SubscriptionDto> getSubscriptions() {

        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionDto> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
