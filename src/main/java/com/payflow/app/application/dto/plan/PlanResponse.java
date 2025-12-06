package com.payflow.app.application.dto.plan;

import com.payflow.app.domain.entities.enums.PlanPeriod;

                           public record PlanResponse(Long id, String cod, String name, String description, Double price, PlanPeriod period, Boolean active) {
}
