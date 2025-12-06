package com.payflow.app.application.dto.plan;

import com.payflow.app.domain.entities.enums.PlanPeriod;

public record PlanRequest(String name,String cod, String description, Double price, PlanPeriod period) {
}
