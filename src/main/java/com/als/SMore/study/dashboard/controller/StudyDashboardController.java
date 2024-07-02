package com.als.SMore.study.dashboard.controller;

import com.als.SMore.study.dashboard.DTO.AttendanceStatusDTO;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;
import com.als.SMore.study.dashboard.DTO.StudyRankingDTO;
import com.als.SMore.study.dashboard.service.StudyDashboardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
        List<StudyMemberDTO> studyMembers = studyDashboardService.getStudyMembers(studyPk);
        return ResponseEntity.ok(studyMembers);
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
     * 스터디 멤버 출석 현황 조회
     *
     * @param studyPk 스터디 PK
     * @return StudyRankingDTO 리스트와 함께 OK 응답 반환
     */
    @GetMapping
    public ResponseEntity<List<AttendanceStatusDTO>> getAttendanceStatus(@PathVariable Long studyPk) {
        List<AttendanceStatusDTO> attendanceStatus = studyDashboardService.getAttendanceStatus(studyPk);
        return ResponseEntity.ok(attendanceStatus);
    }
}