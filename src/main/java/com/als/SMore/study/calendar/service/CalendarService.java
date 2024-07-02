package com.als.SMore.study.calendar.service;



import com.als.SMore.study.calendar.dto.request.CreateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.request.UpdateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.response.FindCalendarResponseDTO;

import java.util.List;

public interface CalendarService {



    void createCalendar(Long memberPk, Long studyPk, CreateCalendarRequestDTO createCalendarRequestDTO);

    void updateCalendar(Long memberPk, Long studyPk, UpdateCalendarRequestDTO updateCalendarRequestDTO);

    void deleteCalendar(Long memberPk, Long studyPk, Long calendarPk);

    FindCalendarResponseDTO findCalendar(Long calendarPk);

    List<FindCalendarResponseDTO> findAllCalendar(Long studyPk);
}
