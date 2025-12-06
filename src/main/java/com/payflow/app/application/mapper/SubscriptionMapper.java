package com.payflow.app.application.mapper;

import com.payflow.app.application.dto.plan.PlanRequest;
import com.payflow.app.application.dto.plan.PlanResponse;
import com.payflow.app.application.dto.subscription.SubsRequest;
import com.payflow.app.domain.entities.plans.Plan;
import com.payflow.app.domain.entities.subscription.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

    Subscription toEntity(SubsRequest dto);

    PlanResponse toDto(Subscription entity);
}






