package com.als.SMore.study.calendar.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UpdateCalendarRequestDTO {
    private Long calendarPk;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

}
