package com.als.SMore.demo.entity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "calender")
public class Calender {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_pk", nullable = false)
    private Long calenderPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Lob
    @Column(name = "calender_content")
    private String calenderContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "calendar_date", nullable = false)
    private Date calendarDate;

    // Getters and Setters
}
