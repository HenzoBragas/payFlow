package com.payflow.app.application.dto.subscription;

import com.payflow.app.domain.entities.plans.Plan;
import com.payflow.app.domain.entities.user.Users;

public record SubsRequest(Users userId, Plan planId) {
}
