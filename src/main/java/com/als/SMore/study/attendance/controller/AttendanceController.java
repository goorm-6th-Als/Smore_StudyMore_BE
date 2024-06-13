package com.als.SMore.study.attendance.controller;

import com.als.SMore.study.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class AttendanceController {
    final private AttendanceService attendanceService;
    private Long getMember(){
        return 585736981395554283L;
    }

    @PostMapping("/{studyPk}/attendance/start")
    public LocalDateTime start(@PathVariable Long studyPk){
        return attendanceService.attendanceStart(getMember(), studyPk);
    }
    @PostMapping("/{studyPk}/attendance/end")
    public String end(@PathVariable Long studyPk){
       attendanceService.attendanceEnd(getMember(), studyPk);
       return "성공";
    }
}
