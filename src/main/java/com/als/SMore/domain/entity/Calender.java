package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "calender")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calender {

    @Id @Tsid
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
}
