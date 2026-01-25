package com.subscription.service.services;

import com.subscription.service.models.dtos.AuthenticatedUserDto;
import com.subscription.service.models.dtos.SessionRequestDto;
import com.subscription.service.models.entity.MentorMenteeSubscription;
import com.subscription.service.models.entity.Session;
import com.subscription.service.models.entity.SessionStatus;
import com.subscription.service.models.entity.SubscriptionStatus;
import com.subscription.service.repository.SessionRepository;
import com.subscription.service.repository.SubscriptionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SessionService(SessionRepository sessionRepository,
                          SubscriptionRepository subscriptionRepository) {
        this.sessionRepository = sessionRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    private Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof AuthenticatedUserDto user)) {
            throw new RuntimeException("Unauthorized");
        }

        return user.getUserId();
    }

    public Session createSession(SessionRequestDto request) {

        Long menteeUserId = getAuthenticatedUserId();

        MentorMenteeSubscription subscription =
                subscriptionRepository
                        .findFirstByMenteeAndStatus(menteeUserId, SubscriptionStatus.ACTIVE)
                        .orElseThrow(() -> new RuntimeException("Active subscription not found"));

        // kredi kontrolü
        if (subscription.getRemainingCredits() <= 0) {
            throw new RuntimeException("No remaining credits");
        }

        // açık session var mı?
        boolean hasOpenSession =
                sessionRepository.existsBySubscriptionAndStatus(
                        subscription,
                        SessionStatus.SCHEDULED
                );

        if (hasOpenSession) {
            throw new RuntimeException("There is already an open session");
        }

        Session session = Session.builder()
                .subscription(subscription.getSubscriptionId())
                .scheduledAt(request.getScheduledAt())
                .status(SessionStatus.SCHEDULED)
                .mentorApproved(false)
                .menteeApproved(true)
                .build();

        return sessionRepository.save(session);
    }
}
