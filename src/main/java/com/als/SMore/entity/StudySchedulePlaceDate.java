package com.als.SMore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "study_schedule_place_date")
public class StudySchedulePlaceDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_schedule_place_date_pk")
    private Long studySchedulePlaceDatePk;

    @ManyToOne
    @JoinColumn(name = "study_schedule_pk", nullable = false)
    private StudySchedule studySchedule;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    // Getters and Setters
}

