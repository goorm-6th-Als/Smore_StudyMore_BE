package com.als.SMore.study.dashboard.controller;

import com.als.SMore.study.dashboard.DTO.MonthlyAttendanceStatusDTO;
import com.als.SMore.study.dashboard.DTO.TodayAttendanceStatusDTO;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;
import com.als.SMore.study.dashboard.DTO.StudyRankingDTO;
import com.als.SMore.study.dashboard.service.StudyDashboardService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{studyPk}/dashboard")
@RequiredArgsConstructor
public class StudyDashboardController {
    private final StudyDashboardService studyDashboardService;

    /**
     * 스터디에 참여중인 모든 멤버를 조회
     *
     * @param studyPk 스터디 PK
     * @return 스터디 멤버 목록과 함께 OK 응답 반환
     */
    @GetMapping("/members")
    public ResponseEntity<List<StudyMemberDTO>> getStudyMembers(
            @PathVariable Long studyPk) {
        List<StudyMemberDTO> studyMembers = studyDashboardService.getAllStudyMembers(studyPk);
        return ResponseEntity.ok(studyMembers);
    }

    /**
     * 스터디 content 조회
     *
     * @param studyPk 스터디 PK
     * @return 스터디 content와 함께 응답
     */
    @GetMapping("/content")
    public ResponseEntity<String> getStudyContent(@PathVariable Long studyPk) {
        String content = studyDashboardService.getStudyContent(studyPk);
        return ResponseEntity.ok(content);
    }

    /**
     * 금일 공부 시간 랭킹 조회
     *
     * @param studyPk 스터디 PK
     * @return StudyRankingDTO 리스트와 함께 OK 응답 반환
     */
    @GetMapping("/ranking")
    public ResponseEntity<List<StudyRankingDTO>> getTodayStudyRanking(
            @PathVariable Long studyPk) {
        List<StudyRankingDTO> rankings = studyDashboardService.getTodayStudyRanking(studyPk);
        return ResponseEntity.ok(rankings);
    }

    /**
     * 스터디 멤버 출석 현황 조회 (오늘)
     *
     * @param studyPk 스터디 PK
     * @return StudyRankingDTO 리스트와 함께 OK 응답 반환
     */
    @GetMapping("/attendance/today")
    public ResponseEntity<List<TodayAttendanceStatusDTO>> getTodayAttendanceStatus(@PathVariable Long studyPk) {
        List<TodayAttendanceStatusDTO> attendanceStatus = studyDashboardService.getTodayAttendanceStatus(studyPk);
        return ResponseEntity.ok(attendanceStatus);
    }


    /**
     * 월별 스터디 출석 현황 조회
     *
     * @param studyPk   스터디 PK
     * @param year
     * @parm month 조회할 연월
     * @return 월별 출석 현황 리스트와 함께 OK 응답 반환
     */
    @GetMapping("/attendance/monthly")
    public ResponseEntity<Map<Integer, List<MonthlyAttendanceStatusDTO>>> getMonthlyAttendanceStatus(
            @PathVariable Long studyPk,
            @RequestParam int year,
            @RequestParam int month) {
        Map<Integer, List<MonthlyAttendanceStatusDTO>> monthlyAttendanceStatus = studyDashboardService.getMonthlyAttendanceStatus(
                studyPk, year, month);
        return ResponseEntity.ok(monthlyAttendanceStatus);
    }

}