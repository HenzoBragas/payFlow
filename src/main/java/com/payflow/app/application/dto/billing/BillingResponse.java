package com.payflow.app.application.dto.billing;


import com.payflow.app.domain.entities.enums.BillingStatus;

import java.time.LocalDate;

public record BillingResponse(Long id, Long idSubscription, Long idUser, String userName,
                              String subscriptionName, Double amount, LocalDate issueDate, LocalDate dueDate,
                              LocalDate paymentDate, BillingStatus status) {
}
