package com.subscription.service.models.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class UserResponse {
    private Long userId;
    private Role role;
}
