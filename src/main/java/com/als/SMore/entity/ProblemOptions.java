package com.als.SMore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "problem_options")
public class ProblemOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_options_pk")
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
