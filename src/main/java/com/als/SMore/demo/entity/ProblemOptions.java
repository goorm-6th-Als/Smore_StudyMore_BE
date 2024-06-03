package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

@Entity
@Table(name = "problem_options")
public class ProblemOptions {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_options_pk", nullable = false)
    private Long problemOptionsPk;

    @ManyToOne
    @JoinColumn(name = "problem_pk", nullable = false)
    private Problem problem;

    @Column(name = "options_num", nullable = false)
    private Integer optionsNum;

    @Column(name = "options_content", nullable = false)
    private String optionsContent;

    // Getters and Setters
}
