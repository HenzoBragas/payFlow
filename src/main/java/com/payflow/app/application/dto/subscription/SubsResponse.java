package com.payflow.app.application.dto.subscription;

import com.payflow.app.domain.entities.enums.StatusSubscription;

import java.time.LocalDate;

public record SubsResponse(Long id, Long userId, Long planId, LocalDate startDate, LocalDate nextBillingDate, StatusSubscription statusSubscription) {
}
