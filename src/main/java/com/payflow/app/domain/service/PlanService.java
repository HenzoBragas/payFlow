package com.payflow.app.domain.service;

import com.payflow.app.application.dto.plan.PlanRequest;
import com.payflow.app.application.dto.plan.PlanResponse;
import com.payflow.app.application.dto.plan.PlanUpdateRequest;
import com.payflow.app.application.mapper.PlanMapper;
import com.payflow.app.domain.entities.plans.Plan;
import com.payflow.app.domain.entities.user.Users;
import com.payflow.app.infrastructure.repository.PlanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository repository;
    private final PlanMapper mapper;

    public PlanService(PlanRepository repository, PlanMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PlanResponse> toList() {
        return convertToDto(repository.findAll());
    }

    public PlanResponse create(PlanRequest req) {
        if(repository.findByCod(req.cod()).isPresent())
            throw new RuntimeException("Email ja cadastrado no sistema");

        Plan newPlan = mapper.toEntity(req);
        repository.save(newPlan);

        return mapper.toDto(newPlan);
    }

    @Transactional
    public PlanResponse update(Long id, PlanUpdateRequest req) {
        Plan soft = findPlanById(id);
        BeanUtils.copyProperties(req, soft, "id");
        Plan updateEntity = repository.save(soft);

        return mapper.toDto(updateEntity);
    }

    @Transactional
    public void setActive(Long id, boolean value){
        Plan soft = findPlanById(id);
        soft.setActive(value);
    }


    public List<PlanResponse> toActiveList() {
        return convertToDto(repository.findAllByActiveTrue());
    }

    public List<PlanResponse> toDeactivateList() {
        return convertToDto(repository.findAllByActiveFalse());
    }

    private Plan findPlanById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano com ID " + id + " não encontrado"));    }

    private List<PlanResponse> convertToDto(List<Plan> entities) {
        return entities.stream()
                .map(mapper::toDto)
                .toList();
    }
}
