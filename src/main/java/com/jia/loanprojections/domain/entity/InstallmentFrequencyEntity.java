package com.jia.loanprojections.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "installment_frequency")
public class InstallmentFrequencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private boolean applicableToMonthly;
    private boolean applicableToWeekly;


}