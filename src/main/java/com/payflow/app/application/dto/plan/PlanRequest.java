package com.payflow.app.application.dto.plan;

import com.payflow.app.domain.entities.enums.PlanPeriod;
import com.payflow.app.domain.entities.enums.StatusPlans;

public record PlanRequest(String name,String cod, String description, Double price, PlanPeriod period, StatusPlans statusPlans) {
}
