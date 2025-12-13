package com.payflow.app.application.controller;

import com.payflow.app.application.dto.pay.WebhookPaymentRequest;
import com.payflow.app.domain.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

    private final BillingService service;

    public WebhookController(BillingService service) {
        this.service = service;
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> receive(@RequestBody WebhookPaymentRequest request) {
        service.processWebhook(request);
        return ResponseEntity.ok().build();
    }
}
