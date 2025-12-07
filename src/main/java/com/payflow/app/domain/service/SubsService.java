package com.payflow.app.domain.service;

import com.payflow.app.application.dto.subscription.SubsRequest;
import com.payflow.app.application.dto.subscription.SubsResponse;
import com.payflow.app.application.mapper.SubscriptionMapper;
import com.payflow.app.domain.entities.enums.StatusSubscription;
import com.payflow.app.domain.entities.plans.Plan;
import com.payflow.app.domain.entities.subscription.Subscription;
import com.payflow.app.domain.entities.user.Users;
import com.payflow.app.infrastructure.repository.PlanRepository;
import com.payflow.app.infrastructure.repository.SubscriptionRepository;
import com.payflow.app.infrastructure.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubsService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionMapper mapper;


    public SubsService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, PlanRepository planRepository, SubscriptionMapper mapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.mapper = mapper;
    }

    public List<SubsResponse> toList(Long userId) {

        List<Subscription> subs;

        if (userId != null) {
            Users user = findUserById(userId); // valida se existe
            subs = subscriptionRepository.findByUserId_Id(userId); // filtra no banco
        } else {
            subs = subscriptionRepository.findAll(); // admin vê todas
        }

        return convertToDto(subs);
    }


    public SubsResponse createSubscription(SubsRequest req) {
        Users user = findUserById(req.userId());
        Plan plan = findPlanById(req.planId());

        Subscription sub = mapper.toEntity(req);

        sub.setUserId(user);
        sub.setPlanId(plan);
        sub.setStartDate(LocalDate.now());
        sub.setNextBillingDate(calculateNextBillingDate(plan));
        sub.setStatusSubscription(StatusSubscription.PENDING_PAYMENT);

        subscriptionRepository.save(sub);
        return mapper.toDto(sub);
    }

    @Transactional
    public void setCancel(Long id) {
        Subscription sub = findSubById(id);
        sub.setStatusSubscription(StatusSubscription.CANCELED);

        subscriptionRepository.save(sub);
    }


    private Plan findPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Plano com ID " + id + " não encontrado"));
    }

    private Subscription findSubById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Assinatura com ID " + id + " não encontrado"));
    }

    private Users findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private LocalDate calculateNextBillingDate(Plan plan) {
        return switch (plan.getPeriod()) {
            case MONTHLY -> LocalDate.now().plusMonths(1);
            case SEMESTER  -> LocalDate.now().plusMonths(6);
            case YEARLY -> LocalDate.now().plusYears(1);
        };
    }

    private List<SubsResponse> convertToDto(List<Subscription> entities) {
        return entities.stream()
                .map(mapper::toDto)
                .toList();
    }

}
