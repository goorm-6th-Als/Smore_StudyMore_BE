package com.als.SMore.study.calendar.dto.response;

import com.als.SMore.global.json.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class FindCalendarResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long calendarPk;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
}
