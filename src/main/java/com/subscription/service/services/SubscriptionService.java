package com.subscription.service.services;

import com.subscription.service.client.UserClient;
import com.subscription.service.models.dtos.SubscriptionRequestDto;
import com.subscription.service.models.entity.MentorMenteeSubscription;
import com.subscription.service.models.entity.SubscriptionStatus;
import com.subscription.service.models.response.Role;
import com.subscription.service.models.response.UserResponse;
import com.subscription.service.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserClient userClient;
    private final static Integer TOTAL_CREDITS = 4;
    private final static Integer REMAINING_CREDITS = 4;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserClient userClient) {
        this.subscriptionRepository = subscriptionRepository;
        this.userClient = userClient;
    }

    public MentorMenteeSubscription createSubscription(SubscriptionRequestDto request) {
        UserResponse mentor = userClient.getUserById(request.getMentorUserId());
        UserResponse mentee = userClient.getUserById(request.getMenteeUserId());

        if(mentor.getRole() != Role.MENTOR){
            throw new RuntimeException("User " + mentor.getUserId() + "is not mentor");
        }
        if(mentee.getRole() != Role.MENTEE){
            throw new RuntimeException("User " + mentee.getUserId() + " is not mentee");
        }

        MentorMenteeSubscription subscription = MentorMenteeSubscription.builder()
                .mentor(mentor.getUserId())
                .mentee(mentee.getUserId())
                .totalCredits(TOTAL_CREDITS)
                .remainingCredits(REMAINING_CREDITS)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        return subscriptionRepository.save(subscription);

    }
}
