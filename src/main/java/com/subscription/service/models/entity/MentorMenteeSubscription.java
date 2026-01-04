package com.subscription.service.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "mentor_mentee_subscription")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MentorMenteeSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @Column(name = "mentor_user_id", nullable = false)
    private Long mentor;

    @Column(name = "mentee_user_id", nullable = false)
    private Long mentee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(name = "subscription_end_date")
    private LocalDateTime subscriptionEndDate;

    @Column(name = "total_credits")
    private Integer totalCredits;

    @Column(name = "remaining_credits")
    private Integer remainingCredits;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.subscriptionEndDate = this.createdAt.plusMonths(2);
    }

}