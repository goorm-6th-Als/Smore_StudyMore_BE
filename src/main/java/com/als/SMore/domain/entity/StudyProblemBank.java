package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_problem_bank")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyProblemBank {

    @Id @Tsid
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
