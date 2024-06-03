package com.als.SMore.demo.entity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "share_schedule")
public class ShareSchedule {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_schedule_pk", nullable = false)
    private Long shareSchedulePk;

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

    // Getters and Setters
}
