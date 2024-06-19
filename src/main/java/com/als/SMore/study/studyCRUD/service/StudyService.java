package com.als.SMore.study.studyCRUD.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyBoardRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.studyCRUD.DTO.StudyCreateDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyService {
    private static final Logger logger = LoggerFactory.getLogger(StudyService.class);
    private final StudyRepository studyRepository;
    private final StudyDetailRepository studyDetailRepository;
    private final MemberRepository memberRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyBoardRepository studyBoardRepository;

    /**
     * 스터디를 생성하는 메서드.
     * 방이 생성되면 studyMemberRepository에 admin으로 저장.
     * @param studyCreateDTO 스터디 생성에 필요한 정보를 담은 DTO 객체
     * @return 생성된 스터디 정보를 담은 StudyCreateDTO 객체
     */
    @Transactional
    public StudyCreateDTO createStudy(StudyCreateDTO studyCreateDTO) {
        Member member = memberRepository.findById(studyCreateDTO.getMemberPk())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 memberPk: " + studyCreateDTO.getMemberPk()));

        Study study = StudyCreateDTO.toEntity(studyCreateDTO, member);
        studyRepository.save(study);
        logger.info("Study 생성: {}", study);

        StudyDetail studyDetail = StudyCreateDTO.toDetailEntity(studyCreateDTO, study);
        studyDetailRepository.save(studyDetail);
        logger.info("StudyDetail 생성: {}", studyDetail);

        StudyMember studyMember = StudyCreateDTO.toMemberEntity(studyCreateDTO, study, member);
        studyMemberRepository.save(studyMember);
        logger.info("StudyMember 생성: {}", studyMember);

        StudyBoard studyBoard = StudyCreateDTO.toBoardEntity(studyCreateDTO, study);
        studyBoardRepository.save(studyBoard);
        logger.info("StudyBoard 생성: {}", studyBoard);

        return StudyCreateDTO.fromEntity(study, studyDetail, studyBoard);
    }


    /**
     * 스터디 이름을 ID로 조회하는 메서드.
     * @param studyPk 스터디 PK
     * @return 스터디 이름
     */
    @Transactional(readOnly = true)
    public String getStudyNameById(Long studyPk) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + studyPk));
        return study.getStudyName();
    }
}
