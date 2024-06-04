package com.als.SMore.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "calender")
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_pk")
    private Long calenderPk;

    @Column(name = "calender_content")
    private String calenderContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "calendar_date", nullable = false)
    private Date calendarDate;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    // Getters and Setters
}

