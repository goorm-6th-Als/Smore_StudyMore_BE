package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "problem_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
