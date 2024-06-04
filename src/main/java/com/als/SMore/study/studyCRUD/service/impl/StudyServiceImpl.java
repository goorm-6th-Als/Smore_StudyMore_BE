package com.als.SMore.study.studyCRUD.service.impl;

import com.als.SMore.study.studyCRUD.DTO.StudyDTO;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.studyCRUD.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private StudyRepository studyRepository;

    @Override
    public Study createStudy(StudyDTO studyDTO) {
        Study study = Study.builder()
                .studyName(studyDTO.getStudyName())

                .build();

        return studyRepository.save(study);
    }
}
