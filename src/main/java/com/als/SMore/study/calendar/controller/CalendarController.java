package com.als.SMore.study.calendar.controller;

import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.global.validator.GlobalValidator;
import com.als.SMore.study.calendar.dto.request.CreateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.request.UpdateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.response.FindCalendarResponseDTO;
import com.als.SMore.study.calendar.service.CalendarService;
import com.als.SMore.study.calendar.validator.CalendarValidator;
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study/{studyPk}/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;
    private final GlobalValidator globalValidator;
    private final CalendarValidator calendarValidator;
    private final StudyMemberRepository studyMemberRepository;

    private void adminValidator(Long studyPk, Long memberPk){
        if(!studyMemberRepository.existsByStudyStudyPkAndMemberMemberPkAndRole(studyPk, memberPk, "admin")){
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_MEMBER);
        }
    }
    private Long getMemberPk() {
        return MemberUtil.getUserPk();
    }


    //일정표 생성
    @PostMapping
    public void createCalendar(@PathVariable("studyPk") Long studyPk,
                               @RequestBody CreateCalendarRequestDTO createCalendarRequestDTO) {
        adminValidator(studyPk, getMemberPk());
        Member member = globalValidator.getMember(getMemberPk());
        Study study = globalValidator.getStudy(studyPk);
        calendarService.createCalendar(member, study, createCalendarRequestDTO);
    }

    //일정표 수정
    @PutMapping
    public void updateCalendar(@PathVariable("studyPk") Long studyPk,
                               @RequestBody UpdateCalendarRequestDTO updateCalendarRequestDTO) {
        adminValidator(studyPk, getMemberPk());
        Member member = globalValidator.getMember(getMemberPk());
        Study study = globalValidator.getStudy(studyPk);
        Calendar calendar = calendarValidator.getCalendar(updateCalendarRequestDTO.getCalendarPk());
        calendarService.updateCalendar(member, study, calendar, updateCalendarRequestDTO);
    }

    @GetMapping("/{calendarPk}")
    public FindCalendarResponseDTO getCalendar(@PathVariable("calendarPk") Long calendarPk) {
        Calendar calendar = calendarValidator.getCalendar(calendarPk);
        return calendarService.findCalendar(calendar);
    }

    @GetMapping("/list")
    public List<FindCalendarResponseDTO> getAllCalendar(@PathVariable("studyPk") Long studyPk) {
        Study study = globalValidator.getStudy(studyPk);
        return calendarService.findAllCalendar(study);
    }

    @DeleteMapping("/{calendarPk}")
    public void deleteCalendar(@PathVariable("studyPk") Long studyPk,
                               @PathVariable("calendarPk") Long calendarPk) {
        adminValidator(studyPk, getMemberPk());
        Member member = globalValidator.getMember(getMemberPk());
        Study study = globalValidator.getStudy(studyPk);
        Calendar calendar = calendarValidator.getCalendar(calendarPk);
        calendarService.deleteCalendar(member, study, calendar);
    }
}
