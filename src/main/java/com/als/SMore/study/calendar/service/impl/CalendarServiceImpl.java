package com.als.SMore.study.calendar.service.impl;


import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.CalendarRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.log.timeTrace.TimeTrace;
import com.als.SMore.study.calendar.dto.request.CreateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.request.UpdateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.response.FindCalendarResponseDTO;
import com.als.SMore.study.calendar.mapper.CalendarMapper;
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

    //validator
    final private CalendarValidator calendarValidator;
    private final StudyMemberRepository studyMemberRepository;

    private boolean isAdmin(Long studyPk, Long memberPk) {
        return studyMemberRepository.existsByStudyStudyPkAndMemberMemberPkAndRole(studyPk, memberPk, "admin");
    }

    private void adminValidator(Long studyPk, Long memberPk){
        if(!isAdmin(studyPk, memberPk)){
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_MEMBER);
        }
    }

    @Override
    public void createCalendar(Long memberPk, Long studyPk, CreateCalendarRequestDTO createCalendarRequestDTO) {
        //검증
        adminValidator(studyPk, memberPk);

        Member member = calendarValidator.getMember(memberPk);
        Study study = calendarValidator.getStudy(studyPk);
        calendarRepository.save(Calendar.builder().
                member(member).
                study(study).
                startDate(createCalendarRequestDTO.getStartDate()).
                endDate(createCalendarRequestDTO.getEndDate()).
                calendarContent(createCalendarRequestDTO.getContent()).
                build());
    }

    @Override
    public void updateCalendar(Long memberPk, Long studyPk, UpdateCalendarRequestDTO updateCalendarRequestDTO) {
        adminValidator(studyPk, memberPk);
        Calendar calendar = calendarValidator.getCalendar(updateCalendarRequestDTO.getCalendarPk());
        calendar.updateContentAndDate(
                updateCalendarRequestDTO.getContent(),
                updateCalendarRequestDTO.getStartDate(),
                updateCalendarRequestDTO.getEndDate()
        );

    }

    @Override
    public void deleteCalendar(Long memberPk, Long studyPk, Long calendarPk) {
        adminValidator(studyPk, memberPk);

        calendarRepository.delete(calendarValidator.getCalendar(calendarPk));
    }

    @Override
    public FindCalendarResponseDTO findCalendar(Long calendarPk) {
        Calendar calendar = calendarValidator.getCalendar(calendarPk);
        return CalendarMapper.calendarToFindCalendarResponseDTO(calendar);

    }

    @Override
    public List<FindCalendarResponseDTO> findAllCalendar(Long studyPk) {
        List<Calendar> calendarList = calendarRepository.findByStudy(calendarValidator.getStudy(studyPk));
        List<FindCalendarResponseDTO> findCalendarResponseDTOList = new ArrayList<>();
        for (Calendar calendar : calendarList) {
            findCalendarResponseDTOList.add(CalendarMapper.calendarToFindCalendarResponseDTO(calendar));
        }
        return findCalendarResponseDTOList;
    }
}
