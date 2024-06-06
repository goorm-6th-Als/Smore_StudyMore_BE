package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "attendance_check")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCheck {

    @Id @Tsid
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
