package com.payflow.app.application.mapper;

import com.payflow.app.application.dto.user.UserRequest;
import com.payflow.app.application.dto.user.UserResponse;
import com.payflow.app.domain.entities.user.Users;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    // Converte DTO de entrada para entidade
    Users toEntity(UserRequest dto);

    // Converte entidade para DTO de saída
    UserResponse toDto(Users entity);

}


