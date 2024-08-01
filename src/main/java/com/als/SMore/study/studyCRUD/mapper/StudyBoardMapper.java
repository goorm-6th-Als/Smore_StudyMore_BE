package com.als.SMore.study.studyCRUD.mapper;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.study.studyCRUD.DTO.StudyBoardDTO;

public class StudyBoardMapper {
    public static StudyBoardDTO toDTO(StudyBoard studyBoard, StudyDetail studyDetail, Study study, Long curPeople) {
        return StudyBoardDTO.builder()
                .studyBoardPk(studyBoard.getStudyBoardPk())
                .studyPk(studyBoard.getStudy().getStudyPk())

                .studyName(study.getStudyName())
                .adTitle(studyBoard.getAdTitle())
                .adContent(studyBoard.getAdContent())
                .imageUri(studyBoard.getImageUri())

                .curPeople(curPeople)
                .maxPeople(studyDetail.getMaxPeople())

                .startDate(studyDetail.getStartDate())
                .closeDate(studyDetail.getCloseDate())
                .modifyDate(studyBoard.getModifyDate())

                .build();
    }
    public static StudyBoardDTO toStudyBoard(StudyBoard studyBoard,
                                                 StudyRepository studyRepository,
                                                 StudyDetailRepository studyDetailRepository,
                                                 StudyMemberRepository studyMemberRepository) {
        long curPeople = studyMemberRepository.countByStudyStudyPk(studyBoard.getStudy().getStudyPk());
        Study study = studyRepository.findById(studyBoard.getStudy().getStudyPk())
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));
        StudyDetail studyDetail = studyDetailRepository.findByStudyStudyPk(studyBoard.getStudy().getStudyPk())
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_DETAIL));
        return toDTO(studyBoard, studyDetail, study, curPeople);
    }
}