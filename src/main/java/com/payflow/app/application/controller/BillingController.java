package com.payflow.app.application.controller;

import com.payflow.app.application.dto.billing.BillingResponse;
import com.payflow.app.domain.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BillingResponse>> listTo(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(service.toList(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingResponse> list(@PathVariable Long billingId) {
        return ResponseEntity.ok(service.list(billingId));
    }
}