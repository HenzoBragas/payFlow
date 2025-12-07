package com.payflow.app.application.mapper;


import com.payflow.app.application.dto.subscription.SubsRequest;
import com.payflow.app.application.dto.subscription.SubsResponse;
import com.payflow.app.domain.entities.subscription.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface SubscriptionMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plan", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "nextBillingDate", ignore = true)
    @Mapping(target = "statusSubscription", ignore = true)
    Subscription toEntity(SubsRequest dto);

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "planName", source = "plan.name")
    SubsResponse toDto(Subscription entity);
}






