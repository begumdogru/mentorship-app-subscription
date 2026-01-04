package com.subscription.service.repository;

import com.subscription.service.models.entity.MentorMenteeSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<MentorMenteeSubscription, Long> {
    Optional<MentorMenteeSubscription> findById(Long id);
}
