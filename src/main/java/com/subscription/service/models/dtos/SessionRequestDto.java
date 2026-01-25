package com.subscription.service.models.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SessionRequestDto {
    private LocalDateTime scheduledAt;
}