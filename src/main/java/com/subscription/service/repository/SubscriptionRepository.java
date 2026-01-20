package com.subscription.service.repository;

import com.subscription.service.models.entity.MentorMenteeSubscription;
import com.subscription.service.models.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<MentorMenteeSubscription, Long> {
    Optional<MentorMenteeSubscription> findById(Long id);

    Optional<MentorMenteeSubscription> existsByMenteeAnd(SubscriptionStatus status);

    boolean existsByMenteeAndRemainingCreditsGreaterThan(
            Long mentee,
            Integer remainingCredits
    );
    boolean existsByMenteeAndSubscriptionEndDate(Long mentee, LocalDateTime subscriptionEndDate);
    Optional<MentorMenteeSubscription>
    findFirstByMentorAndMenteeAndStatus(
            Long mentor,
            Long mentee,
            SubscriptionStatus status
    );
}
