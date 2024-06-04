package com.als.SMore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "problem_type")
public class ProblemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_type_pk")
    private Long problemTypePk;

    @ManyToOne
    @JoinColumn(name = "problem_pk", nullable = false)
    private Problem problem;

    @Column(name = "type_content")
    private String typeContent;

    // Getters and Setters
}
