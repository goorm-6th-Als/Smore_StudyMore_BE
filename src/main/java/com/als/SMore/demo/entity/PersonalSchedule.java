package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personal_schedule")
public class PersonalSchedule {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_schedule_pk", nullable = false)
    private Long personalSchedulePk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Column(name = "schedule_status", nullable = false)
    private String scheduleStatus;

    @Lob
    @Column(name = "schedule_content")
    private String scheduleContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_date", nullable = false)
    private Date scheduleDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    // Getters and Setters
}
