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
import com.als.SMore.study.dashboard.DTO.MonthlyAttendanceStatusDTO;
import com.als.SMore.study.dashboard.DTO.StudyDetailDTO;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;
import com.als.SMore.study.dashboard.DTO.StudyRankingDTO;
import com.als.SMore.study.dashboard.DTO.TodayAttendanceStatusDTO;
import com.als.SMore.study.dashboard.mapper.MonthlyAttendanceStatusMapper;
import com.als.SMore.study.dashboard.mapper.StudyDetailMapper;
import com.als.SMore.study.dashboard.mapper.StudyMemberMapper;
import com.als.SMore.study.dashboard.mapper.StudyRankingMapper;
import com.als.SMore.study.dashboard.mapper.TodayAttendanceStatusMapper;
import com.als.SMore.study.studyCRUD.service.StudyService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public List<StudyMemberDTO> getAllStudyMembers(Long studyPk) {
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
     * 스터디 정보 조회
     *
     * @param studyPk 스터디 PK
     * @return 스터디 content
     */
    @Transactional(readOnly = true)
    public StudyDetailDTO getStudyContent(Long studyPk) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));
        StudyDetail studyDetail = studyDetailRepository.findByStudy_StudyPk(studyPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));
        return StudyDetailMapper.toDTO(study,studyDetail);
    }

    /**
     * 금일 공부 시간 랭킹 조회
     *
     * @param studyPk 스터디 PK
     * @return StudyRankingDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyRankingDTO> getTodayStudyRanking(Long studyPk) {
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
     * 오늘의 출석 현황 조회
     *
     * @param studyPk 스터디 PK
     * @return 오늘의 출석 현황 리스트
     */
    @Transactional(readOnly = true)
    public List<TodayAttendanceStatusDTO> getTodayAttendanceStatus(Long studyPk) {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);

        List<StudyMember> studyMembers = studyMemberRepository.findByStudyStudyPk(studyPk);

        return studyMembers.stream()
                .map(studyMember -> {
                    Member member = studyMember.getMember();
                    Study study = studyMember.getStudy();
                    boolean isAttended = attendanceCheckRepository.existsByMemberAndStudyAndAttendanceDateBetween(member, study, todayStart, todayEnd);
                    Optional<AttendanceCheck> attendanceCheck = attendanceCheckRepository.findFirstByMemberAndStudyAndAttendanceDateBetweenOrderByAttendanceDateAsc(member, study, todayStart, todayEnd);
                    LocalDateTime attendanceDate = attendanceCheck.map(AttendanceCheck::getAttendanceDate).orElse(LocalDateTime.MAX);
                    String timeAgo = attendanceCheck.map(ac -> TodayAttendanceStatusMapper.calculateTimeAgo(ac.getAttendanceDate())).orElse("결석");
                    return TodayAttendanceStatusMapper.toDTO(member, isAttended ? "출석" : "결석", attendanceDate, timeAgo);
                })
                .sorted(Comparator.comparing(TodayAttendanceStatusDTO::getAttendanceDate))
                .collect(Collectors.toList());
    }

    /**
     * 특정 달의 출석 현황 조회
     *
     * @param studyPk 스터디 PK
     * @param year
     * @param month
     * @return 특정 달의 출석 현황 리스트
     */
    @Transactional(readOnly = true)
    public Map<Integer, List<MonthlyAttendanceStatusDTO>> getMonthlyAttendanceStatus(Long studyPk, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        List<StudyMember> studyMembers = studyMemberRepository.findByStudyStudyPk(studyPk);
        Map<Integer, List<MonthlyAttendanceStatusDTO>> monthlyAttendanceStatus = new HashMap<>();

        for (int day = 1; day <= endDate.getDayOfMonth(); day++) {
            LocalDate currentDate = startDate.withDayOfMonth(day);
            LocalDateTime dayStart = currentDate.atStartOfDay();
            LocalDateTime dayEnd = dayStart.plusDays(1);

            List<MonthlyAttendanceStatusDTO> dailyStatus = studyMembers.stream()
                    .map(studyMember -> MonthlyAttendanceStatus(studyMember, dayStart, dayEnd))
                    .filter(Objects::nonNull) // 출석한 경우만 필터링
                    .collect(Collectors.toList());

            monthlyAttendanceStatus.put(day, dailyStatus);
        }

        return monthlyAttendanceStatus;
    }

    /**
     * 멤버가 언제 언제 출석했는지만 반환
     * @return 특정 달의 출석 현황 리스트
     */
    private MonthlyAttendanceStatusDTO MonthlyAttendanceStatus(StudyMember studyMember, LocalDateTime startDate, LocalDateTime endDate) {
        Member member = studyMember.getMember();
        Study study = studyMember.getStudy();
        Optional<AttendanceCheck> attendanceCheck = attendanceCheckRepository.findFirstByMemberAndStudyAndAttendanceDateBetweenOrderByAttendanceDateAsc(member, study, startDate, endDate);

        if (attendanceCheck.isPresent()) {
            LocalDate attendanceDate = LocalDate.from(attendanceCheck.get().getAttendanceDate());
            return MonthlyAttendanceStatusMapper.toDTO(member, "출석", attendanceDate);
        }
        return null; // 출석하지 않은 경우 null 반환
    }
}