package com.subscription.service.models.entity;

import lombok.Getter;

@Getter
public enum SubscriptionStatus {
    CREATED,
    ACTIVE,
    COMPLETED,
    CANCELLED
}
