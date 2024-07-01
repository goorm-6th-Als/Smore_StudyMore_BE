package com.als.SMore.study.dashboard.service;
import com.als.SMore.domain.entity.StudyLearningTime;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.StudyLearningTimeRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;
import com.als.SMore.study.dashboard.DTO.StudyRankingDTO;
import com.als.SMore.study.dashboard.mapper.StudyMemberMapper;
import com.als.SMore.study.dashboard.mapper.StudyRankingMapper;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyDashboardService {
    private final StudyMemberRepository studyMemberRepository;
    private final StudyLearningTimeRepository studyLearningTimeRepository;

    /**
     * 스터디에 참여중인 모든 멤버를 조회
     * @param studyPk 스터디 PK
     * @return StudyMemberDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyMemberDTO> getStudyMembers(Long studyPk) {
        List<StudyMember> studyMembers = studyMemberRepository.findByStudyStudyPk(studyPk);

        if (studyMembers.isEmpty()) {
            throw new CustomException(CustomErrorCode.NOT_FOUND_STUDY);
        }
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

        // 스터디 멤버가 없으면 CustomException 발생
        if (studyMembers.isEmpty()) {
            throw new CustomException(CustomErrorCode.NOT_FOUND_STUDY);
        }

        // 각 멤버의 오늘 공부 시간을 Map<Member, Long>으로 그룹화 및 합산
        Map<Member, Long> studyTimes = studyMembers.stream()
                .flatMap(studyMember -> studyLearningTimeRepository.findByStudyMemberAndLearningDate(studyMember, today).stream())
                .collect(Collectors.groupingBy(
                        studyLearningTime -> studyLearningTime.getStudyMember().getMember(),
                        Collectors.summingLong(StudyLearningTime::getLearningTime)
                ));

        // 공부 시간 순으로 정렬 후 StudyRankingDTO 리스트로 변환
        return studyTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> StudyRankingMapper.toDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}