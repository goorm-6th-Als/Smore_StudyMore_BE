package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "attendance_check")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_check_pk")
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
}
