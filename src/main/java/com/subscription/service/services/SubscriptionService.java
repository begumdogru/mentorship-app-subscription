package com.subscription.service.services;

import com.subscription.service.client.UserClient;
import com.subscription.service.models.dtos.AuthenticatedUserDto;
import com.subscription.service.models.dtos.SubscriptionRequestDto;
import com.subscription.service.models.entity.MentorMenteeSubscription;
import com.subscription.service.models.entity.SubscriptionStatus;
import com.subscription.service.models.response.Role;
import com.subscription.service.models.response.UserResponse;
import com.subscription.service.repository.SubscriptionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private Long getAuthenticatedUserId(){
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof AuthenticatedUserDto user)) {
            throw new RuntimeException("Unauthorized");
        }

        return user.getUserId();
    }

    public MentorMenteeSubscription createSubscription(SubscriptionRequestDto request) {

        Long menteeUserId = getAuthenticatedUserId();

        UserResponse mentee = userClient.getUserById(menteeUserId);
        UserResponse mentor = userClient.getUserById(request.getUserId());

        if (mentor.getRole() != Role.MENTOR) {
            throw new RuntimeException("User is not mentor");
        }

        if (mentee.getRole() != Role.MENTEE) {
            throw new RuntimeException("User is not mentee");
        }
        Optional<MentorMenteeSubscription> activeSub =
                subscriptionRepository.findFirstByMentorAndMenteeAndStatus(
                        mentor.getUserId(),
                        menteeUserId,
                        SubscriptionStatus.ACTIVE
                );

        if (activeSub.isPresent()) {
            MentorMenteeSubscription sub = activeSub.get();

            boolean hasCredits = sub.getRemainingCredits() > 0;
            boolean notExpired =
                    sub.getSubscriptionEndDate() != null &&
                            sub.getSubscriptionEndDate().isAfter(LocalDateTime.now());

            if (hasCredits && notExpired) {
                throw new RuntimeException(
                        "Active subscription still valid. Cannot create new one."
                );
            }
        }

        MentorMenteeSubscription subscription =
                MentorMenteeSubscription.builder()
                        .mentor(mentor.getUserId())
                        .mentee(mentee.getUserId())
                        .totalCredits(TOTAL_CREDITS)
                        .remainingCredits(REMAINING_CREDITS)
                        .status(SubscriptionStatus.ACTIVE)
                        .build();

        return subscriptionRepository.save(subscription);
    }
}
