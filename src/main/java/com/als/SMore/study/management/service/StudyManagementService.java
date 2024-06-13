package com.als.SMore.study.management.service;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.repository.StudyBoardRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyRepository;
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

    /**
     * 스터디를 업데이트하는 메서드.
     * @param studyPk 업데이트할 스터디의 PK
     * @param studyUpdateDTO 업데이트할 스터디 정보를 담은 DTO 객체
     * @return 업데이트된 스터디 정보를 담은 StudyUpdateDTO 객체
     */
    @Transactional
    public StudyUpdateDTO updateStudy(Long studyPk, StudyUpdateDTO studyUpdateDTO) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + studyPk));
        StudyDetail studyDetail = studyDetailRepository.findByStudy(study);
        StudyBoard studyBoard = studyBoardRepository.findByStudy(study);

        StudyDetail updatedStudyDetail = studyUpdateDTO.updateDetailEntity(studyDetail);
        StudyBoard updatedStudyBoard = studyUpdateDTO.updateBoardEntity(studyBoard);

        studyDetailRepository.save(updatedStudyDetail);
        studyBoardRepository.save(updatedStudyBoard);

        return StudyUpdateDTO.fromEntity(updatedStudyDetail);
    }

    /**
     * 스터디를 삭제하는 메서드.
     * @param studyPk 삭제할 스터디의 PK
     */
    @Transactional
    public void deleteStudy(Long studyPk) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + studyPk));
        studyRepository.delete(study);
    }
}
