package com.payflow.app.application.controller;


import com.payflow.app.application.dto.subscription.SubsRequest;
import com.payflow.app.application.dto.subscription.SubsResponse;
import com.payflow.app.domain.service.SubsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubController {

    private final SubsService service;

    public SubController(SubsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubsResponse>> list(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(service.toList(userId));
    }

    @PostMapping
    public ResponseEntity<SubsResponse> create(@RequestBody SubsRequest req){
        SubsResponse response = service.createSubscription(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        service.setCancel(id);
        return ResponseEntity.noContent().build();
    }

}
