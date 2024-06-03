package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

@Entity
@Table(name = "problem_type")
public class ProblemType {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_type_pk", nullable = false)
    private Long problemTypePk;

    @ManyToOne
    @JoinColumn(name = "problem_pk", nullable = false)
    private Problem problem;

    @Column(name = "type_content")
    private String typeContent;

    // Getters and Setters
}
