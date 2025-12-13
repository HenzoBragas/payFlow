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


    @Mapping(source = "user.id", target = "idUser")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "subscription.id", target = "idSubscription")
    @Mapping(source = "subscription.plan.name", target = "subscriptionName")
    BillingResponse toDto(Billing entity);

}

