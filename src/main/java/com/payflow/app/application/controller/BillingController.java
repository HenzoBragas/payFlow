package com.payflow.app.application.controller;

import com.payflow.app.application.dto.billing.BillingResponse;
import com.payflow.app.application.dto.pay.PaidResponse;
import com.payflow.app.domain.service.BillingService;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/{billingId}/pay")
    public ResponseEntity<PaidResponse> toPaid(@PathVariable Long billingId) {
        PaidResponse response = service.pay(billingId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BillingResponse>> listTo(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(service.toList(userId));
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<BillingResponse> getById(@PathVariable Long billingId) {
        return ResponseEntity.ok(service.getById(billingId));
    }



}