package com.als.SMore.study.studyCRUD.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberRepository;
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

    /**
     * 스터디를 생성하는 메서드.
     * 방이 생성되면 studyMemberRepository에 admin으로 저장.
     * @param studyCreateDTO 스터디 생성에 필요한 정보를 담은 DTO 객체
     * @return 생성된 스터디 정보를 담은 StudyCreateDTO 객체
     */
    @Transactional
    public StudyCreateDTO createStudy(StudyCreateDTO studyCreateDTO) {
        // 멤버를 memberRepository에서 조회
        Member member = memberRepository.findById(studyCreateDTO.getMemberPk())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 memberPk: " + studyCreateDTO.getMemberPk()));

        // Study 엔티티 생성
        Study study = Study.builder()
                .studyName(studyCreateDTO.getStudyName())
                .member(member)
                .build();
        studyRepository.save(study);
        logger.info("Study 생성: {}", study);

        // StudyDetail 엔티티 생성
        StudyDetail studyDetail = StudyDetail.builder()
                .study(study) // Study 참조 설정
                .imageUri(studyCreateDTO.getImageUri())
                .maxPeople(studyCreateDTO.getMaxPeople())
                .overview(studyCreateDTO.getOverview())
                .openDate(studyCreateDTO.getOpenDate())
                .closeDate(studyCreateDTO.getCloseDate())
                .build();

        studyDetailRepository.save(studyDetail);
        logger.info("StudyDetail 생성: {}", studyDetail);

        // StudyMember 엔티티 생성 및 저장
        StudyMember studyMember = StudyMember.builder()
                .study(study)
                .member(member)
                .role("admin")
                .build();
        studyMemberRepository.save(studyMember);
        logger.info("StudyMember 생성: {}", studyMember);

        // 스터디 페이지 URL 설정 - Pk로
        String studyUrl = "http://localhost:8080/study/" + study.getStudyPk();
        // 생성된 StudyCreateDTO 객체 반환
        studyCreateDTO.setStudyPk(study.getStudyPk());
        studyCreateDTO.setStudyUrl(studyUrl);

        return studyCreateDTO;
    }

    /**
     * 스터디를 삭제하는 메서드.
     * @param id 삭제할 스터디의 ID
     */
    @Transactional
    public void deleteStudy(Long id) {
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + id));
        studyRepository.delete(study);
        logger.info("스터디 삭제: {}", id);
    }

    /**
     * 스터디를 업데이트하는 메서드.
     * @param id 업데이트할 스터디의 ID
     * @param studyCreateDTO 업데이트할 스터디 정보를 담은 DTO 객체
     * @return 업데이트된 스터디 정보를 담은 StudyCreateDTO 객체
     */
    @Transactional
    public StudyCreateDTO updateStudy(Long id, StudyCreateDTO studyCreateDTO) {
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + id));

        study.setStudyName(studyCreateDTO.getStudyName());
        studyRepository.save(study);

        StudyDetail studyDetail = studyDetailRepository.findByStudy(study);
        studyDetail.setImageUri(studyCreateDTO.getImageUri());
        studyDetailRepository.save(studyDetail);

        return studyCreateDTO;
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
