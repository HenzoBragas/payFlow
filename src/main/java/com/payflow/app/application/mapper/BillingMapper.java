package com.payflow.app.application.mapper;

import com.payflow.app.application.dto.billing.BillingRequest;
import com.payflow.app.application.dto.billing.BillingResponse;
import com.payflow.app.domain.entities.billing.Billing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface BillingMapper {

    @Mapping(target = "subscription", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "issueDate", ignore = true)
    @Mapping(target = "dueDate", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    Billing toEntity(BillingRequest dto);

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "subscriptionName", source = "subscription.plan.name")
    BillingResponse toDto(Billing entity);

}

