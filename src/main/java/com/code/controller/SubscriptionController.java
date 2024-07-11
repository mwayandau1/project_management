package com.code.controller;


import com.code.model.PlanType;
import com.code.model.Subscription;
import com.code.model.User;
import com.code.service.SubscriptionService;
import com.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        if(user == null){
            throw new Exception("Please login to continue");
        }

        Subscription subscription = subscriptionService.getSubscriptionByUserId(user.getId());
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }



    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(@RequestHeader("Authorization") String jwt, @RequestBody PlanType planType) throws Exception{
        User user= userService.findUserProfileByJwt(jwt);
        if(user == null){
            throw new Exception("Please login to continue");
        }
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
