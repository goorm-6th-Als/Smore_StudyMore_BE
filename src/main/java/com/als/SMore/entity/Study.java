package com.als.SMore.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "study")
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_pk")
    private Long studyPk;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Column(name = "study_name", nullable = false)
    private String studyName;

    @OneToMany(mappedBy = "study")
    private Set<StudyBoard> studyBoards;

    // Getters and Setters
}
