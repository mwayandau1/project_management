package com.code.service;

import com.code.model.PlanType;
import com.code.model.Subscription;
import com.code.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user) throws Exception;


    Subscription getSubscriptionByUserId(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception;

    boolean isSubscriptionValid(Subscription subscription) throws Exception;
}
