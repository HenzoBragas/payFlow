package com.payflow.app.domain.entities.plans;


import com.payflow.app.domain.entities.enums.PlanPeriod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plans")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cod;

    private String name;
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private PlanPeriod period;

    @Column(nullable = false)
    private Boolean active = true;
}
