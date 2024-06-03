package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

@Entity
@Table(name = "share_schedule_place_date")
public class ShareSchedulePlaceDate {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_schedule_place_date_pk", nullable = false)
    private Long shareSchedulePlaceDatePk;

    @ManyToOne
    @JoinColumn(name = "share_schedule_pk", nullable = false)
    private ShareSchedule shareSchedule;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    // Getters and Setters
}
