package com.als.SMore.study.calendar.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreateCalendarRequestDTO {
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

}
