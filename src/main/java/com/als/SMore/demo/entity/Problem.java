package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "problem")
public class Problem {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_pk", nullable = false)
    private Long problemPk;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "problem_code_bank_pk", nullable = false)
    private StudyProblemBank problemCodeBank;

    @Lob
    @Column(name = "problem_content")
    private String problemContent;

    @Column(name = "problem_answer")
    private Integer problemAnswer;

    @Lob
    @Column(name = "problem_explanation")
    private String problemExplanation;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    // Getters and Setters
}
