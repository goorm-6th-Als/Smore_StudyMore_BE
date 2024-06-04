package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "problem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_pk")
    private Long problemPk;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "problem_code_bank_pk", nullable = false)
    private StudyProblemBank studyProblemBank;

    @Column(name = "problem_content")
    private String problemContent;

    @Column(name = "problem_answer")
    private Integer problemAnswer;

    @Column(name = "problem_explanation")
    private String problemExplanation;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;
}
