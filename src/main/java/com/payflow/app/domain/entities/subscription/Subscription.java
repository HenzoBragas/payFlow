package com.payflow.app.domain.entities.subscription;

import com.payflow.app.domain.entities.enums.StatusSubscription;
import com.payflow.app.domain.entities.plans.Plan;
import com.payflow.app.domain.entities.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@NoArgsConstructor
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private LocalDate startDate;
    private LocalDate nextBillingDate;

    @Enumerated(EnumType.STRING)
    private StatusSubscription statusSubscription;
}
