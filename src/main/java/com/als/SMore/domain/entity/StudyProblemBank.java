package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_problem_bank")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudyProblemBank {

    @Id @Tsid
    @Column(name = "study_problem_bank_pk")
    private Long studyProblemBankPk;

    @ManyToOne  //그룹 만든 사람 스터디장과 함께 삭제 권한이 있는 사람
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "bank_name", nullable = false)
    private String bankName;
}
