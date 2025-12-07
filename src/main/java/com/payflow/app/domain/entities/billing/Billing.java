package com.payflow.app.domain.entities.billing;

import com.payflow.app.domain.entities.enums.BillingStatus;
import com.payflow.app.domain.entities.subscription.Subscription;
import com.payflow.app.domain.entities.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "billing")
@Getter
@Setter
@NoArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
   private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Double amount;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private BillingStatus status;

}
