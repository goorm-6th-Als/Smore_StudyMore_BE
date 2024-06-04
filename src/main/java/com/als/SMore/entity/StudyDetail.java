package com.als.SMore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "study_detail")
public class StudyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_pk")
    private Long studyPk;

    @Column(name = "image_uri")
    private String imageUri;

    @Column(name = "max_people", nullable = false)
    private Integer maxPeople;

    @Column(name = "overview")
    private String overview;

    @Column(name = "open_time", nullable = false)
    private String openTime = "00:00:00";

    @Column(name = "close_time", nullable = false)
    private String closeTime = "23:59:59";

    // Getters and Setters
}
