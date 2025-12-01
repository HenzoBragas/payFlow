package com.payflow.app.infrastructure.repository;

import com.payflow.app.domain.entities.plans.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
