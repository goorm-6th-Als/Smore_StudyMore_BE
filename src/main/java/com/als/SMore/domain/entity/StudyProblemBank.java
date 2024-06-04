package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "study_problem_bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyProblemBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_code_bank_pk")
    private Long problemCodeBankPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "study_name", nullable = false)
    private String studyName;

    @Column(name = "group_name", nullable = false)
    private String groupName;
}
