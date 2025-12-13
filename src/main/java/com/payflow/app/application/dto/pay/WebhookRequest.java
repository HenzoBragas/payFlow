package com.payflow.app.application.dto.pay;

public record WebhookRequest(Long billingId,
                             String event,
                             String transactionId
) {
}
