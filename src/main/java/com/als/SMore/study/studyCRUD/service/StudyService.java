package com.als.SMore.study.studyCRUD.service;

import com.als.SMore.study.studyCRUD.DTO.StudyDTO;
import com.als.SMore.domain.entity.Study;

public interface StudyService {
    Study createStudy(StudyDTO studyDTO);
}
