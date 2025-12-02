package com.payflow.app.application.mapper;

import com.payflow.app.application.dto.plan.PlanRequest;
import com.payflow.app.application.dto.plan.PlanResponse;
import com.payflow.app.domain.entities.plans.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlanMapper {

    // Converte DTO de entrada para entidade
    Plan toEntity(PlanRequest dto);

    // Converte entidade para DTO de saída
    PlanResponse toDto(Plan entity);

}


