package com.payflow.app.domain.service;


import com.payflow.app.application.dto.plan.PlanResponse;
import com.payflow.app.application.dto.user.UserRequest;
import com.payflow.app.application.dto.user.UserResponse;
import com.payflow.app.application.mapper.UserMapper;
import com.payflow.app.domain.entities.plans.Plan;
import com.payflow.app.domain.entities.user.Users;
import com.payflow.app.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private  final UserRepository repository;
    private  final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public List<UserResponse> list() {
        return convertToDto(repository.findAll());
    }

    public UserResponse create(UserRequest req) {

        if (repository.findByEmail(req.email()).isPresent()) {
            throw new RuntimeException("Email ja cadastrado no sistema");
        }

        Users newUser = mapper.toEntity(req);
        repository.save(newUser);

        // Retorna DTO de resposta
        return mapper.toDto(newUser);
    }

    @Transactional
    public UserResponse atualizar(Long id, UserRequest req){

        Users userUpdate = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontraado"));

        BeanUtils.copyProperties(req, userUpdate, "id");
        Users updateEntity = repository.save(userUpdate);

        return mapper.toDto(updateEntity);
    }

    private List<UserResponse> convertToDto(List<Users> entities) {
        return entities.stream()
                .map(mapper::toDto)
                .toList();
    }

}
