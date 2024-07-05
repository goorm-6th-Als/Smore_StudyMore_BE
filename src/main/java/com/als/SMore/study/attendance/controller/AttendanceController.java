package com.als.SMore.study.attendance.controller;

import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthListResponseDTO;
import com.als.SMore.study.attendance.service.AttendanceService;
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/study/{studyPk}/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    final private AttendanceService attendanceService;


    /**
     * SecurityContext에 저장된 유저의 pk를 조회
     * @return MemberPk
     */
    private Long getMember(){
        return MemberUtil.getUserPk();
    }

    /**
     * 출석 시작
     * @param studyPk 스터디 Pk
     * @return db에 저장된 시간을 반환
     */
    @PostMapping("/start")
    public LocalDateTime start(@PathVariable Long studyPk){
        return attendanceService.attendanceStart(getMember(), studyPk);
    }

    /**
     * attendanceCheck에 출석 종료 시간 업데이트
     * @param studyPk
     * @return
     */

    @PostMapping("/stop")
    public Long end(@PathVariable Long studyPk){
       return attendanceService.attendanceEnd(getMember(), studyPk);
    }

    @GetMapping("/my-study-time")
    public Long getTime(@PathVariable Long studyPk){
        return attendanceService.getLearningSeconds(getMember(), studyPk);
    }

    @GetMapping("/my-study-month")
    public LearningMonthListResponseDTO getMonth(@PathVariable Long studyPk, @RequestBody LearningMonthRequestDTO learningMonthRequestDTO){
        System.out.println(learningMonthRequestDTO.getMonth());
        return attendanceService.getLearningMonth(getMember(), studyPk, learningMonthRequestDTO);

    }

}
