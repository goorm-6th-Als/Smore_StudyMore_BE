package com.als.SMore.study.studyCRUD.controller;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.study.studyCRUD.DTO.StudyDTO;
import com.als.SMore.study.studyCRUD.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudyCreateController {

    @Autowired
    private StudyService studyService;

    @PostMapping("/study")
    public ResponseEntity<String> createStudy(@RequestBody StudyDTO studyDTO) {
        Study study = studyService.createStudy(studyDTO);
        String url = "/study/" + study.getStudyName();  // Assuming study name is unique
        return ResponseEntity.ok(url);
    }
}