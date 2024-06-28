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
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.studyCRUD.DTO.StudyCreateDTO;
import com.als.SMore.study.studyCRUD.mapper.StudyCreateMapper;
import com.als.SMore.user.login.util.MemberUtil;
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

    private static final int MAX_STUDY_PARTICIPATION = 5;
    /**
     * 스터디 생성
     * @param studyCreateDTO 생성할 스터디의 정보를 담은 DTO
     * @return 생성된 스터디 정보를 담은 DTO와 함께 응답
     */
    @Transactional
    public StudyCreateDTO createStudy(StudyCreateDTO studyCreateDTO) {
        Long memberPk = MemberUtil.getUserPk();
        logger.info("현재 사용자 PK: {}", memberPk);

        // 참여할 수 있는 스터디 최대 갯수 설정
        if (studyMemberRepository.countByMemberMemberPk(memberPk) >= MAX_STUDY_PARTICIPATION) {
            throw new CustomException(CustomErrorCode.MAX_STUDY_PARTICIPATION_EXCEEDED);
        }
        Member member = memberRepository.findById(memberPk)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 memberPk: " + memberPk));

        Study study = StudyCreateMapper.toStudy(studyCreateDTO, member);
        studyRepository.save(study);
        logger.info("Study 엔티티 생성 Study PK: {}", study.getStudyPk());

        StudyDetail studyDetail = StudyCreateMapper.toStudyDetail(studyCreateDTO, study);
        studyDetailRepository.save(studyDetail);
        logger.info("StudyDetail 엔티티 생성");

        StudyMember studyMember = StudyCreateMapper.toStudyMember(studyCreateDTO, study, member);
        studyMemberRepository.save(studyMember);
        logger.info("StudyMember 엔티티 생성");

        StudyBoard studyBoard = StudyCreateMapper.toStudyBoard(studyCreateDTO, study);
        studyBoardRepository.save(studyBoard);
        logger.info("StudyBoard 엔티티 생성 StudyBoard PK: {}", studyBoard.getStudyBoardPk());
        return StudyCreateDTO.fromEntity(study, studyDetail);
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
