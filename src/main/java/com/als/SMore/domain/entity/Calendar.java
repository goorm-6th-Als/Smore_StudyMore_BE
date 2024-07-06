package com.als.SMore.domain.entity;

import com.als.SMore.study.calendar.dto.request.CreateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.request.UpdateCalendarRequestDTO;
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

    public void updateContentAndDate(UpdateCalendarRequestDTO updateCalendarRequestDTO){
        this.calendarContent = updateCalendarRequestDTO.getContent();
        this.startDate = updateCalendarRequestDTO.getStartDate();
        this.endDate = updateCalendarRequestDTO.getEndDate();
    }

    public static Calendar of(Member member, Study study, CreateCalendarRequestDTO createCalendarRequestDTO){
        return Calendar.builder().
                member(member).
                study(study).
                startDate(createCalendarRequestDTO.getStartDate()).
                endDate(createCalendarRequestDTO.getEndDate()).
                calendarContent(createCalendarRequestDTO.getContent()).
                build();
    }


}
