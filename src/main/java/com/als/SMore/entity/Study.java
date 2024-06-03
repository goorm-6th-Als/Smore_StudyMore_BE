package com.als.SMore.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

@Entity
@Table(name = "study")
public class Study {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_pk", nullable = false)
    private Long studyPk;

    @OneToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member leader;

    @Column(name = "study_name", nullable = false)
    private String studyName;

    // Getters and Setters
}
