package com.payflow.app.application.dto.pay;

public record WebhookPaymentRequest(Long billingId,
                                    String event,
                                    String transactionId
) {
}
