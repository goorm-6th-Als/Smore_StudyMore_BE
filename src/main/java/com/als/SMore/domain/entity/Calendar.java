package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "calendar")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {

    @Id @Tsid
    @Column(name = "calendar_pk")
    private Long calendarPk;

    @Column(name = "calendar_content")
    private String calendarContent;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    public void updateContentAndDate(String calendarContent, LocalDate startDate, LocalDate endDate){
        this.calendarContent = calendarContent;
        this.startDate = startDate;
        this.endDate = endDate;
    }


}
