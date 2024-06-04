package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "study_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_schedule_pk")
    private Long studySchedulePk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Column(name = "schedule_status")
    private String scheduleStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "target_date")
    private Date targetDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
}
