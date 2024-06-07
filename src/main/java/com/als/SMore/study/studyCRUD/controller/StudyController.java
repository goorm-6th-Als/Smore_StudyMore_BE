package com.als.SMore.study.studyCRUD.controller;

import com.als.SMore.study.studyCRUD.DTO.StudyCreateDTO;
import com.als.SMore.study.studyCRUD.service.StudyService;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController -> 타임리프라 잠시 지움.

@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    // 스터디 생성
    @PostMapping
    public ResponseEntity<StudyCreateDTO> createStudy(@RequestBody StudyCreateDTO studyCreateDTO) {
        StudyCreateDTO createdStudy = studyService.createStudy(studyCreateDTO);
        return ResponseEntity.created(URI.create(createdStudy.getStudyUrl())).body(createdStudy);
    }

    // 스터디 ID로 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long id) {
        studyService.deleteStudy(id);
        return ResponseEntity.noContent().build();
    }

    // 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<StudyCreateDTO> updateStudy(@PathVariable Long id, @RequestBody StudyCreateDTO studyCreateDTO) {
        StudyCreateDTO updatedStudy = studyService.updateStudy(id, studyCreateDTO);
        return ResponseEntity.ok(updatedStudy);
    }

    @GetMapping("/{studyPk}")
    public String getStudy(@PathVariable Long studyPk, Model model) {
        String studyName = studyService.getStudyNameById(studyPk);
        model.addAttribute("studyPk", studyPk);
        model.addAttribute("studyName", studyName);
        return "studyPage";
    }

    // 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("무슨 에러냐면 : " + e.getMessage());
    }

    // JSON 관련
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<String> handleJsonMappingException(JsonMappingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Json 관련 에러 발생 : " + ex.getMessage());
    }
}