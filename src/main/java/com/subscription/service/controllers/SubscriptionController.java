package com.subscription.service.controllers;

import com.subscription.service.models.dtos.SubscriptionRequestDto;
import com.subscription.service.models.entity.MentorMenteeSubscription;
import com.subscription.service.services.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/create")
    public ResponseEntity<MentorMenteeSubscription> createMentorMenteeSubscription(@RequestBody SubscriptionRequestDto requestDto) {
        MentorMenteeSubscription subscription = subscriptionService.createSubscription(requestDto);
        //TODO:: BİR ÜYE AYNI PAKETTEN ART ARDA ALINCA NASIL BİR BUSINESS RULE İŞLEMELİ?
        return ResponseEntity.ok(subscription);
    }

}
