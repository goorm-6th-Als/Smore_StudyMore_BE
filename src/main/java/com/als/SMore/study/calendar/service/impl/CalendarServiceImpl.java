package com.als.SMore.study.calendar.service.impl;


import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.CalendarRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.log.timeTrace.TimeTrace;
import com.als.SMore.study.calendar.dto.request.CreateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.request.UpdateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.response.FindCalendarResponseDTO;
import com.als.SMore.study.calendar.service.CalendarService;
import com.als.SMore.study.calendar.validator.CalendarValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@TimeTrace
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    //Repository
    final private CalendarRepository calendarRepository;

    @Override
    public void createCalendar(Member member, Study study, CreateCalendarRequestDTO createCalendarRequestDTO) {
        calendarRepository.save(Calendar.of(member, study, createCalendarRequestDTO));
    }

    @Override
    public void updateCalendar(Member member, Study study, Calendar calendar, UpdateCalendarRequestDTO updateCalendarRequestDTO) {
        calendar.updateContentAndDate(updateCalendarRequestDTO);

    }

    @Override
    public void deleteCalendar(Member member, Study study, Calendar calendar) {
        calendarRepository.delete(calendar);
    }

    @Override
    public FindCalendarResponseDTO findCalendar(Calendar calendar) {
        return FindCalendarResponseDTO.of(calendar);

    }

    @Override
    public List<FindCalendarResponseDTO> findAllCalendar(Study study) {
        List<Calendar> calendarList = calendarRepository.findByStudy(study);
        List<FindCalendarResponseDTO> findCalendarResponseDTOList = new ArrayList<>();
        for (Calendar calendar : calendarList) {
            findCalendarResponseDTOList.add(FindCalendarResponseDTO.of(calendar));
        }
        return findCalendarResponseDTOList;
    }
}
