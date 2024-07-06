package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "attendance_check")
@Getter @Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private LocalDateTime attendanceDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "attendance_date_end")
    private LocalDateTime attendanceDateEnd;


    public static AttendanceCheck of(Member member, Study study, LocalDateTime attendanceDate) {
        return AttendanceCheck
                .builder()
                .attendanceDate(attendanceDate)
                .attendanceDateEnd(attendanceDate)
                .study(study)
                .member(member)
                .build();
    }

    public void updateAttendanceDateEnd() {
        this.attendanceDateEnd = LocalDateTime.now();
    }

}
