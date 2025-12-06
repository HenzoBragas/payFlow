package com.payflow.app.infrastructure.repository;

import com.payflow.app.domain.entities.plans.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findByCod(String cod);

    List<Plan> findAllByActiveTrue();
    List<Plan> findAllByActiveFalse();

}
