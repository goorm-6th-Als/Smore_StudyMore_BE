package com.als.SMore.study.calendar.service;



import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.study.calendar.dto.request.CreateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.request.UpdateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.response.FindCalendarResponseDTO;

import java.util.List;

public interface CalendarService {



    void createCalendar(Member member, Study study, CreateCalendarRequestDTO createCalendarRequestDTO);

    void updateCalendar(Member member, Study study,Calendar calendar, UpdateCalendarRequestDTO updateCalendarRequestDTO);

    void deleteCalendar(Member member, Study study, Calendar calendar);

    FindCalendarResponseDTO findCalendar(Calendar calendar);

    List<FindCalendarResponseDTO> findAllCalendar(Study study);
}
