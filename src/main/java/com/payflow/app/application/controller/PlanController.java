package com.payflow.app.application.controller;

import com.payflow.app.application.dto.plan.PlanRequest;
import com.payflow.app.application.dto.plan.PlanResponse;
import com.payflow.app.application.dto.plan.PlanUpdateRequest;
import com.payflow.app.domain.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService service;

    public PlanController(PlanService service) {
        this.service = service;
    }


    @GetMapping("/list")
    public ResponseEntity<List<PlanResponse>> listPlans() {
        return ResponseEntity.status(HttpStatus.OK).body(service.toList());
    }

    @GetMapping("/active")
    public ResponseEntity<List<PlanResponse>> listPlansActive() {
        return ResponseEntity.status(HttpStatus.OK).body(service.toActiveList());
    }

    @GetMapping("/deactivate")
    public ResponseEntity<List<PlanResponse>> listPlansDesactive() {
        return ResponseEntity.status(HttpStatus.OK).body(service.toDeactivateList());
    }

    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@RequestBody PlanRequest req) {
        PlanResponse newPlan = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> updatePlan(@PathVariable Long id, @RequestBody PlanUpdateRequest req) {
        PlanResponse plan =  service.update(id, req);
        return ResponseEntity.ok(plan);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activatePlan(@PathVariable Long id) {
        service.setActive(id, true);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePlan(@PathVariable Long id) {
        service.setActive(id, true);
        return ResponseEntity.noContent().build();
    }


}
