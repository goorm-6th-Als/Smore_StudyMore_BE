package com.als.SMore.study.attendance.controller;

import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthResponseDTO;
import com.als.SMore.study.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class AttendanceController {
    final private AttendanceService attendanceService;
    private Long getMember(){

        return 588968050131947446L;
    }

    @PostMapping("/{studyPk}/attendance/start")
    public LocalDateTime start(@PathVariable Long studyPk){
        System.out.println("들어왔어요");
        return attendanceService.attendanceStart(getMember(), studyPk);
    }
    @PostMapping("/{studyPk}/attendance/end")
    public Long end(@PathVariable Long studyPk){
       return attendanceService.attendanceEnd(getMember(), studyPk);
    }
    @GetMapping("/{studyPk}/attendance/my-study-time")
    public Long getTime(@PathVariable Long studyPk){
        return attendanceService.getLearningSeconds(getMember(), studyPk);
    }
    @GetMapping("/{studyPk}/attendance/my-study-month")
    public List<LearningMonthResponseDTO> getMonth(@PathVariable Long studyPk, @RequestBody LearningMonthRequestDTO learningMonthRequestDTO){
        System.out.println(learningMonthRequestDTO.getMonth());
        return attendanceService.getLearningMonth(getMember(), studyPk, learningMonthRequestDTO);

    }

}
