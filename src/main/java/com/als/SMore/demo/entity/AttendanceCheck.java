package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attendance_check")
public class AttendanceCheck {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_check_pk", nullable = false)
    private Long attendanceCheckPk;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "attendance_date")
    private Date attendanceDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "attendance_date_end")
    private Date attendanceDateEnd;

    // Getters and Setters
}
