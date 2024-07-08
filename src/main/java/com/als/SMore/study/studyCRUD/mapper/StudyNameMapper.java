package com.als.SMore.study.studyCRUD.mapper;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.study.studyCRUD.DTO.StudyNameDTO;

public class StudyNameMapper {

     public static StudyNameDTO toDTO(Study study) {
          return StudyNameDTO.builder()
                  .studyName(study.getStudyName())
                  .build();
     }
}
