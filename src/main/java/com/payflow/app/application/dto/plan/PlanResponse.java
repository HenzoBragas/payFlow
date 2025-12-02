package com.payflow.app.application.dto.plan;

import com.payflow.app.domain.entities.enums.PlanPeriod;
import com.payflow.app.domain.entities.enums.StatusPlans;

public record PlanResponse(Long id, String name, String description, Double price, PlanPeriod period, StatusPlans) {
}
