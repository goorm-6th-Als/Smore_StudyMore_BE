package com.als.SMore.study.management.mapper;

import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.study.management.DTO.StudyBoardUpdateDTO;
import java.time.LocalDate;

public class StudyBoardMapper {

    public static StudyBoard updateStudyBoard(StudyBoard studyBoard, StudyBoardUpdateDTO studyBoardUpdateDTO, String imageUrl) {
        return studyBoard.toBuilder()
                .adTitle(studyBoardUpdateDTO.getAdTitle() != null ? studyBoardUpdateDTO.getAdTitle() : studyBoard.getAdTitle())
                .adContent(studyBoardUpdateDTO.getAdContent() != null ? studyBoardUpdateDTO.getAdContent() : studyBoard.getAdContent())
                .imageUri(imageUrl != null ? imageUrl : studyBoard.getImageUri())
                .modifyDate(LocalDate.now())
                .build();
    }
}

