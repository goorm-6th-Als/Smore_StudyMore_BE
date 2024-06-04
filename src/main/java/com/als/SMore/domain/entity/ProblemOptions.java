package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "problem_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
