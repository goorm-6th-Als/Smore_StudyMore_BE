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
     * @param studyPk 스터디 PK
     * @return StudyEnterMemberDTO 객체
     */
    @Transactional
    public StudyEnterMemberDTO createStudyEnterMember(StudyEnterMemberDTO studyEnterMemberDTO, Long studyPk) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 ID: " + studyPk));
        Member member = memberRepository.findById(studyEnterMemberDTO.getMemberPk())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 ID: " + studyEnterMemberDTO.getMemberPk()));

        StudyEnterMember studyEnterMember = StudyEnterMemberDTO.toEntity(studyEnterMemberDTO, study, member);
        studyEnterMember = studyEnterMemberRepository.save(studyEnterMember);
        return StudyEnterMemberDTO.fromEntity(studyEnterMember);
    }

    /**
     * 특정 스터디의 모든 가입 신청서 조회
     * @param studyPk 스터디 PK
     * @return 해당 스터디의 모든 StudyEnterMemberDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyEnterMemberDTO> getAllStudyEnterMembers(Long studyPk) {
        return studyEnterMemberRepository.findByStudyStudyPk(studyPk).stream()
                .map(StudyEnterMemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 멤버의 모든 가입 신청서 조회
     * @param memberPk 멤버 PK
     * @return 해당 멤버의 모든 StudyEnterMemberDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyEnterMemberDTO> getAllStudyEnterMembersByMember(Long memberPk) {
        return studyEnterMemberRepository.findByMemberMemberPk(memberPk).stream()
                .map(StudyEnterMemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     *  스터디 가입 신청서 삭제
     * @param studyEnterMemberPk 삭제할 StudyEnterMember의 PK
     */
    @Transactional
    public void deleteStudyEnterMember(Long studyEnterMemberPk) {
        studyEnterMemberRepository.deleteById(studyEnterMemberPk);
    }

    /**
     *  스터디 가입 신청서 수정
     * @param studyEnterMemberPk 수정할 StudyEnterMember의 PK
     * @param studyEnterMemberDTO 수정할 내용이 담긴 StudyEnterMemberDTO 객체
     * @return 수정된 StudyEnterMemberDTO 객체
     */
    @Transactional
    public StudyEnterMemberDTO updateStudyEnterMember(Long studyEnterMemberPk, StudyEnterMemberDTO studyEnterMemberDTO) {
        StudyEnterMember studyEnterMember = studyEnterMemberRepository.findById(studyEnterMemberPk)
                .orElseThrow(() -> new IllegalArgumentException("가입 신청서가 존재하지 않습니다: " + studyEnterMemberPk));
        studyEnterMember = studyEnterMemberDTO.updateEntity(studyEnterMember);
        studyEnterMember = studyEnterMemberRepository.save(studyEnterMember);
        return StudyEnterMemberDTO.fromEntity(studyEnterMember);
    }
}
