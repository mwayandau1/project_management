package com.code.service;


import com.code.model.PlanType;
import com.code.model.Subscription;
import com.code.model.User;
import com.code.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserService userService;


    @Override
    public Subscription createSubscription(User user) throws Exception {
        Subscription subscription = new Subscription();

        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getSubscriptionByUserId(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);

        if(!isSubscriptionValid(subscription)){
            subscription.setSubscriptionEndDate(LocalDate.now());
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionStartDate(LocalDate.now().plusMonths(12));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception {
        Subscription subscription = getSubscriptionByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planType == PlanType.MONTHLY){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }

        else {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isSubscriptionValid(Subscription subscription) throws Exception {

        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }

        LocalDate dueDate = subscription.getSubscriptionEndDate();
        LocalDate dateNow = LocalDate.now();

        return dateNow.isBefore(dueDate) || dueDate.equals(dateNow);
    }
}
