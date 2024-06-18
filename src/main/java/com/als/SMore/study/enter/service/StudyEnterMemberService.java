package com.als.SMore.study.enter.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyEnterMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyEnterMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.enter.DTO.StudyEnterMemberDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyEnterMemberService {

    private final StudyEnterMemberRepository studyEnterMemberRepository;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    /**
     * 스터디 가입신청 생성
     * @param studyEnterMemberDTO StudyEnterMemberDTO 객체
     * @return  StudyEnterMemberDTO 객체
     */
    @Transactional
    public StudyEnterMemberDTO createStudyEnterMember(StudyEnterMemberDTO studyEnterMemberDTO) {
        Study study = studyRepository.findById(studyEnterMemberDTO.getStudyPk())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 ID: " + studyEnterMemberDTO.getStudyPk()));
        Member member = memberRepository.findById(studyEnterMemberDTO.getMemberPk())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 ID: " + studyEnterMemberDTO.getMemberPk()));

        StudyEnterMember studyEnterMember = StudyEnterMemberDTO.toEntity(studyEnterMemberDTO, study, member);
        studyEnterMember = studyEnterMemberRepository.save(studyEnterMember);
        return StudyEnterMemberDTO.fromEntity(studyEnterMember);
    }

    /**
     * 스터디 가입 신청서 조회
     * @return 모든 StudyEnterMemberDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyEnterMemberDTO> getAllStudyEnterMembers() {
        return studyEnterMemberRepository.findAll().stream()
                .map(StudyEnterMemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * StudyEnterMember 삭제합니다.
     * @param studyEnterMemberPk 삭제할 StudyEnterMember의 PK
     */
    @Transactional
    public void deleteStudyEnterMember(Long studyEnterMemberPk) {
        studyEnterMemberRepository.deleteById(studyEnterMemberPk);
    }
}
