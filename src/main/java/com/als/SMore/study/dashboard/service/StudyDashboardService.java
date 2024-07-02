package com.als.SMore.study.dashboard.service;

import com.als.SMore.domain.entity.AttendanceCheck;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyLearningTime;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.AttendanceCheckRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyLearningTimeRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.dashboard.DTO.AttendanceStatusDTO;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;
import com.als.SMore.study.dashboard.DTO.StudyRankingDTO;
import com.als.SMore.study.dashboard.mapper.AttendanceStatusMapper;
import com.als.SMore.study.dashboard.mapper.StudyMemberMapper;
import com.als.SMore.study.dashboard.mapper.StudyRankingMapper;
import com.als.SMore.study.studyCRUD.service.StudyService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyDashboardService {
    private static final Logger logger = LoggerFactory.getLogger(StudyService.class);
    private final StudyMemberRepository studyMemberRepository;
    private final StudyLearningTimeRepository studyLearningTimeRepository;
    private final AttendanceCheckRepository attendanceCheckRepository;
    private final StudyDetailRepository studyDetailRepository;
    private final StudyRepository studyRepository;

    /**
     * 스터디에 참여중인 모든 멤버를 조회
     *
     * @param studyPk 스터디 PK
     * @return StudyMemberDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyMemberDTO> getStudyMembers(Long studyPk) {
        validateStudyExists(studyPk);
        List<StudyMember> studyMembers = studyMemberRepository.findByStudyStudyPk(studyPk);

        // 방장 맨 앞에, 들어온 순서부터 reversed 로 comparing
        // 6월 10 > 6월 20
        Comparator<StudyMember> comparator = Comparator
                .comparing((StudyMember member) -> "admin".equals(member.getRole()))
                .reversed()
                .thenComparing(StudyMember::getEnterDate);

        return studyMembers.stream()
                .sorted(comparator)
                .map(StudyMemberMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 스터디 content 조회
     *
     * @param studyPk 스터디 PK
     * @return 스터디 content
     */
    @Transactional(readOnly = true)
    public String getStudyContent(Long studyPk) {
        StudyDetail studyDetail = studyDetailRepository.findByStudy_StudyPk(studyPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));
        return studyDetail.getContent();
    }

    /**
     * 금일 공부 시간 랭킹 조회
     *
     * @param studyPk 스터디 PK
     * @return StudyRankingDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyRankingDTO> getTodayStudyRanking(Long studyPk) {
        validateStudyExists(studyPk);
        LocalDate today = LocalDate.now();

        // 스터디에 속한 모든 멤버 조회
        List<StudyMember> studyMembers = studyMemberRepository.findByStudyStudyPk(studyPk);

        // 각 멤버의 오늘 공부 시간을 Map으로 정리
        Map<Member, Long> studyTimes = studyMembers.stream()
                .flatMap(studyMember -> studyLearningTimeRepository.findByStudyMemberAndLearningDate(studyMember, today)
                        .stream())
                .collect(Collectors.groupingBy(
                        studyLearningTime -> studyLearningTime.getStudyMember().getMember(),
                        Collectors.summingLong(StudyLearningTime::getLearningTime)
                ));

        // 공부 시간 순으로 정렬
        return studyTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> StudyRankingMapper.toDTO(
                        entry.getKey(),
                        entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 모든 스터디 멤버의 출석 현황 조회
     *
     * @param studyPk 스터디 PK
     * @return 출석 현황 리스트
     */
    @Transactional(readOnly = true)
    public List<AttendanceStatusDTO> getAttendanceStatus(Long studyPk) {
        validateStudyExists(studyPk);
        List<StudyMember> studyMembers = studyMemberRepository.findByStudyStudyPk(studyPk);
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);

        return studyMembers.stream()
                // 해당 멤버가 우리 스터디에 출석한 시간대가 오늘 날짜 시간 범위 사이라면 출석으로,
                // 이후 출석한 순으로 정렬
                .map(studyMember -> {
                    Member member = studyMember.getMember();
                    Study study = studyMember.getStudy();
                    boolean isAttended = attendanceCheckRepository.existsByMemberAndStudyAndAttendanceDateBetween(
                            member, study, todayStart, todayEnd);
                    Optional<AttendanceCheck> attendanceCheck = attendanceCheckRepository.findFirstByMemberAndStudyAndAttendanceDateBetweenOrderByAttendanceDateAsc(
                            member, study, todayStart, todayEnd);
                    LocalDateTime attendanceDate = attendanceCheck.map(AttendanceCheck::getAttendanceDate)
                            .orElse(LocalDateTime.MAX);
                    String timeAgo = attendanceCheck.map(
                            ac -> AttendanceStatusMapper.calculateTimeAgo(ac.getAttendanceDate())).orElse("결석");
                    return AttendanceStatusMapper.fromEntity(member, isAttended ? "출석" : "결석", attendanceDate, timeAgo);
                })
                .sorted(Comparator.comparing(AttendanceStatusDTO::getAttendanceDate))
                .collect(Collectors.toList());
    }

    /**
     * 스터디가 존재하는지 확인하는 메서드
     *
     * @param studyPk 스터디 PK
     */
    private void validateStudyExists(Long studyPk) {
        if (!studyRepository.existsById(studyPk)) {
            throw new CustomException(CustomErrorCode.NOT_FOUND_STUDY);
        }
    }
}