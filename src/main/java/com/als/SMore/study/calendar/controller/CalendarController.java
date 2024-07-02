package com.als.SMore.study.calendar.controller;

import com.als.SMore.study.calendar.dto.request.CreateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.request.UpdateCalendarRequestDTO;
import com.als.SMore.study.calendar.dto.response.FindCalendarResponseDTO;
import com.als.SMore.study.calendar.service.CalendarService;
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

    final private CalendarService calendarService;

    private Long getMemberPk() {
        return MemberUtil.getUserPk();
    }


    //일정표 생성
    @PostMapping
    public void createCalendar(@PathVariable("studyPk") Long studyPk,
                               @RequestBody CreateCalendarRequestDTO createCalendarRequestDTO) {
        calendarService.createCalendar(getMemberPk(), studyPk, createCalendarRequestDTO);
    }

    //일정표 수정
    @PutMapping
    public void updateCalendar(@PathVariable("studyPk") Long studyPk,
                               @RequestBody UpdateCalendarRequestDTO updateCalendarRequestDTO) {
        calendarService.updateCalendar(getMemberPk(), studyPk, updateCalendarRequestDTO);
    }

    @GetMapping("/{calendarPk}")
    public FindCalendarResponseDTO getCalendar(@PathVariable("calendarPk") Long calendarPk) {
        return calendarService.findCalendar(calendarPk);
    }

    @GetMapping("/list")
    public List<FindCalendarResponseDTO> getAllCalendar(@PathVariable("studyPk") Long studyPk) {
        return calendarService.findAllCalendar(studyPk);
    }

    @DeleteMapping("/{calendarPk}")
    public void deleteCalendar(@PathVariable("studyPk") Long studyPk,
                               @PathVariable("calendarPk") Long calendarPk) {
        calendarService.deleteCalendar(getMemberPk(), studyPk, calendarPk);
    }
}
