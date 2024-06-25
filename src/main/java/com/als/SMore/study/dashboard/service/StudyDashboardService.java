package com.als.SMore.study.dashboard.service;

import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyDashboardService {
    private final StudyMemberRepository studyMemberRepository;

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
                .map(StudyMemberDTO::fromEntity)
                .collect(Collectors.toList());
    }
}