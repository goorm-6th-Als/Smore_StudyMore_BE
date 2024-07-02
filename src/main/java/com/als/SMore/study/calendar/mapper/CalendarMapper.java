package com.als.SMore.study.calendar.mapper;

import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.study.calendar.dto.response.FindCalendarResponseDTO;


public class CalendarMapper {
    public static FindCalendarResponseDTO calendarToFindCalendarResponseDTO(Calendar calendar) {
        return FindCalendarResponseDTO.builder().
                content(calendar.getCalendarContent()).
                calendarPk(calendar.getCalendarPk()).
                startDate(calendar.getStartDate()).
                endDate(calendar.getEndDate()).
                build();
    }

}
