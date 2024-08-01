package com.als.SMore.study.attendance.controller;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.global.validator.GlobalValidator;
import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthListResponseDTO;
import com.als.SMore.study.attendance.service.AttendanceService;
import com.als.SMore.study.attendance.validator.AttendanceValidator;
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/study/{studyPk}/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final GlobalValidator globalValidator;
    private final AttendanceValidator attendanceValidator;

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
        Member member = globalValidator.getMember(getMember());
        Study study = globalValidator.getStudy(studyPk);
        return attendanceService.attendanceStart(member, study);
    }

    /**
     * attendanceCheck에 출석 종료 시간 업데이트
     * @param studyPk
     * @return
     */

    @PostMapping("/stop")
    public Long end(@PathVariable Long studyPk){
        Member member = globalValidator.getMember(getMember());
        Study study = globalValidator.getStudy(studyPk);
        return attendanceService.attendanceEnd(member, study);
    }

    @GetMapping("/my-study-time")
    public Long getTime(@PathVariable Long studyPk){
        Member member = globalValidator.getMember(getMember());
        Study study = globalValidator.getStudy(studyPk);
        StudyMember studyMember = attendanceValidator.getStudyMember(member,study);
        return attendanceService.getLearningSeconds(studyMember);
    }

    @GetMapping("/my-study-month")
    public LearningMonthListResponseDTO getMonth(@PathVariable Long studyPk, @RequestBody LearningMonthRequestDTO learningMonthRequestDTO){
        Member member = globalValidator.getMember(getMember());
        Study study = globalValidator.getStudy(studyPk);
        StudyMember studyMember = attendanceValidator.getStudyMember(member,study);
        return attendanceService.getLearningMonth(studyMember, learningMonthRequestDTO);

    }

}
