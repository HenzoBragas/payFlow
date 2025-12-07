package com.payflow.app.infrastructure.repository;

import com.payflow.app.domain.entities.billing.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    List<Billing> findBySubscriptionUserId(Long userId);

}
