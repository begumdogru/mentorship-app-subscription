package com.subscription.service.models.dtos;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SubscriptionRequestDto {
    private Long mentorUserId;
    private Long menteeUserId;
}
