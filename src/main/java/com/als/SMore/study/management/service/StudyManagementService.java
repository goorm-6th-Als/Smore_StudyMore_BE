package com.als.SMore.study.management.service;

import static com.als.SMore.global.CustomErrorCode.CustomErrorCode;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.repository.StudyBoardRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.management.DTO.StudyUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyManagementService {
    private final StudyRepository studyRepository;
    private final StudyDetailRepository studyDetailRepository;
    private final StudyBoardRepository studyBoardRepository;
    private final StudyMemberRepository studyMemberRepository;

    /**
     * 스터디를 업데이트하는 메서드
     * @param studyPk 업데이트할 스터디의 PK
     * @param studyUpdateDTO 업데이트할 스터디 정보를 담은 DTO 객체
     * @return 업데이트된 스터디 정보를 담은 StudyUpdateDTO 객체
     */
    @Transactional
    public StudyUpdateDTO updateStudy(Long studyPk, StudyUpdateDTO studyUpdateDTO) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + studyPk));
        StudyDetail studyDetail = studyDetailRepository.findByStudy(study);
        StudyBoard studyBoard = studyBoardRepository.findByAdTitleContainingIgnoreCaseOrderByModifyDateDesc(study);

        StudyDetail updatedStudyDetail = studyUpdateDTO.updateDetailEntity(studyDetail);
        StudyBoard updatedStudyBoard = studyUpdateDTO.updateBoardEntity(studyBoard);

        studyDetailRepository.save(updatedStudyDetail);
        studyBoardRepository.save(updatedStudyBoard);

        return StudyUpdateDTO.fromEntity(updatedStudyDetail);
    }

    /**
     * 스터디를 삭제하는 메서드
     * @param studyPk 삭제할 스터디의 PK
     */
    @Transactional
    public void deleteStudy(Long studyPk, Long memberPk) throws CustomException {
        if (!isAdmin(studyPk, memberPk)) {
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_TODO);
        }
        if (!areAllMembersAdmins(studyPk)) {
            throw new CustomException(CustomErrorCode.STILL_EXISTS_MEMBERS);
        }
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + studyPk));
        studyRepository.delete(study);
    }

    /**
     * 스터디 장인지 확인하는 메서드
     * @param studyPk 스터디 PK
     * @param memberPk 멤버 PK
     * @return 스터디 장이면 true, 아니면 false 반환
     */
    @Transactional(readOnly = true)
    public boolean isAdmin(Long studyPk, Long memberPk) {
        return studyMemberRepository.existsByStudyStudyPkAndMemberMemberPkAndRole(studyPk, memberPk, "admin");
    }

    /**
     * 스터디장만 존재하는지 확인하는 메서드
     * @param studyPk 스터디 PK
     * @return 스터디에 admin만 존재하면 true, 아니면 false 반환
     */
    @Transactional(readOnly = true)
    public boolean areAllMembersAdmins(Long studyPk) {
        long totalMembers = studyMemberRepository.countByStudyStudyPk(studyPk);
        long adminMembers = studyMemberRepository.countByStudyStudyPkAndRole(studyPk, "admin");
        return totalMembers == adminMembers;
    }
}
