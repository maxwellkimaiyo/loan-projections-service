package com.jia.loanprojectionsservice.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "loan_duration")
public class LoanDurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private int minDuration;
    private int maxDuration;
    private int daysInDuration;

}