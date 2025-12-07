package com.payflow.app.application.mapper;


import com.payflow.app.application.dto.subscription.SubsRequest;
import com.payflow.app.application.dto.subscription.SubsResponse;
import com.payflow.app.domain.entities.subscription.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "planId", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "nextBillingDate", ignore = true)
    @Mapping(target = "statusSubscription", ignore = true)
    Subscription toEntity(SubsRequest dto);

    @Mapping(target = "userName", source = "userId.name")
    @Mapping(target = "planName", source = "planId.name")
    SubsResponse toDto(Subscription entity);
}






