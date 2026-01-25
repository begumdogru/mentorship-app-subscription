package com.subscription.service.repository;

import com.subscription.service.models.entity.MentorMenteeSubscription;
import com.subscription.service.models.entity.Session;
import com.subscription.service.models.entity.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findById(Long id);

    boolean existsBySubscriptionAndStatus(MentorMenteeSubscription subscription, SessionStatus sessionStatus);
}
