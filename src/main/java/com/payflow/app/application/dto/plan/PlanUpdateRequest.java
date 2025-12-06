package com.payflow.app.application.dto.plan;

import com.payflow.app.domain.entities.enums.PlanPeriod;

public record PlanUpdateRequest(String name, String description, Double price, PlanPeriod period) {
}

