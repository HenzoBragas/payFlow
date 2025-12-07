package com.payflow.app.domain.service;

import com.payflow.app.application.dto.billing.BillingRequest;
import com.payflow.app.application.dto.billing.BillingResponse;
import com.payflow.app.application.mapper.BillingMapper;
import com.payflow.app.domain.entities.billing.Billing;
import com.payflow.app.domain.entities.enums.BillingStatus;
import com.payflow.app.domain.entities.plans.Plan;
import com.payflow.app.domain.entities.subscription.Subscription;
import com.payflow.app.domain.entities.user.Users;
import com.payflow.app.infrastructure.repository.BillingRepository;
import com.payflow.app.infrastructure.repository.SubscriptionRepository;
import com.payflow.app.infrastructure.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BillingMapper mapper ;


    public BillingService(BillingRepository billingRepository, UserRepository userRepository, BillingMapper mapper, SubscriptionRepository subscriptionRepository) {
        this.billingRepository = billingRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mapper = mapper;
    }


    @RabbitListener(queues = "billing-queue")
    public void createBilling(BillingRequest req) {
        try {

            Users user = findUserById(req.userId());
            Subscription subscription = findSubscriptionById(req.subscriptionId());

            Billing billing = mapper.toEntity(req);

            billing.setUser(user);
            billing.setSubscription(subscription);
            billing.setIssueDate(LocalDate.now()); //Data emissão
            billing.setDueDate(calculateNextBillingDate(subscription.getPlan())); // Data de vencimento
            billing.setPaymentDate(null);
            billing.setAmount(subscription.getPlan().getPrice()); //Preço
            billing.setStatus(BillingStatus.PENDING); // estado

            billingRepository.save(billing);
        } catch (Exception e) {
            // Log ou mecanismo de retry
            e.printStackTrace();
        }
    }

    public List<BillingResponse> toList(Long userId) {
        List<Billing> billings;

        if (userId != null) {
            Users user = findUserById(userId); // valida se existe
            billings = billingRepository.findBySubscriptionUserId(userId); // filtra no banco
        } else {
            billings = billingRepository.findAll(); // admin vê todas
        }

        return convertToDto(billings);
    }

    public BillingResponse list(Long billingId) {
        Billing billing = findBillingById(billingId);

        return mapper.toDto(billing);
    }

    private List<BillingResponse> convertToDto(List<Billing> entities) {
        return entities.stream()
                .map(mapper::toDto)
                .toList();
    }

    private Billing findBillingById(Long id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fatura não encontrado"));

    }

    private Users findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private Subscription findSubscriptionById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrado"));

    }

    private LocalDate calculateNextBillingDate(Plan plan) {
        return switch (plan.getPeriod()) {
            case MONTHLY -> LocalDate.now().plusMonths(1);
            case SEMESTER  -> LocalDate.now().plusMonths(6);
            case YEARLY -> LocalDate.now().plusYears(1);
        };
    }


}
