package com.payflow.app.application.dto.pay;

import com.payflow.app.domain.entities.enums.BillingStatus;

public record PaidResponse(Long billingId, String paymentUrl, String transactionId, BillingStatus billingStatus) {
}
