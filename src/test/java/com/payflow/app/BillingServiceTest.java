package com.payflow.app.domain.service;

import com.payflow.app.application.dto.pay.WebhookPaymentRequest;
import com.payflow.app.domain.entities.billing.Billing;
import com.payflow.app.domain.entities.enums.BillingStatus;
import com.payflow.app.infrastructure.repository.BillingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private BillingRepository billingRepository;

    @InjectMocks
    private BillingService billingService;

    private Billing billing;

    @BeforeEach
    void setup() {
        billing = new Billing();
        billing.setId(1L);
        billing.setAmount(100.0);
        billing.setStatus(BillingStatus.PENDING);
        billing.setDueDate(LocalDate.now().plusDays(3));
    }

    @Test
    void shouldMarkBillingAsPaidWhenWebhookApproved() {
        // GIVEN
        WebhookPaymentRequest request = new WebhookPaymentRequest(
                1L,
                "payment_approved",
                "tx_123"
        );

        when(billingRepository.findById(1L))
                .thenReturn(Optional.of(billing));

        // WHEN
        billingService.processWebhook(request);

        // THEN
        assertEquals(BillingStatus.PAID, billing.getStatus());
        assertEquals(LocalDate.now(), billing.getPaymentDate());

        verify(billingRepository, times(1)).save(billing);
    }

    @Test
    void should_mark_billing_as_failed_when_payment_fails() {
        // ARRANGE (dado que...)
        Billing billing = new Billing();
        billing.setId(1L);
        billing.setStatus(BillingStatus.PENDING);
        billing.setDueDate(LocalDate.now());

        when(billingRepository.findById(1L))
                .thenReturn(Optional.of(billing));

        WebhookPaymentRequest request = new WebhookPaymentRequest(
                1L,
                "payment_failed",
                "tx_123"
        );

        // ACT (quando...)
        billingService.processWebhook(request);

        // ASSERT (então...)
        assertEquals(BillingStatus.FAILED, billing.getStatus());

        verify(billingRepository).save(billing);
    }
}
