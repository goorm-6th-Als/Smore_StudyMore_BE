package com.als.SMore.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "study_detail")
public class StudyDetail {
    @Id @Tsid
    @Column(name = "study_pk", nullable = false)
    private Long studyPk;

    @OneToOne
    @MapsId
    @JoinColumn(name = "study_pk")
    private Study study;

    @Column(name = "image_uri")
    private String imageUri;

    @Column(name = "max_people", nullable = false)
    private Integer maxPeople;

    @Lob
    @Column(name = "overview")
    private String overview;

    @Column(name = "open_time", nullable = false)
    private Time openTime;

    @Column(name = "close_time", nullable = false)
    private Time closeTime;

    // Getters and Setters
}
