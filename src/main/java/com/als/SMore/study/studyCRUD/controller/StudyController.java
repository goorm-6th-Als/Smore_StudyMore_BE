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

@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    /**
     * 스터디 생성
     * @param studyCreateDTO 생성할 스터디의 정보를 담은 DTO
     * @return 생성된 스터디 정보를 담은 DTO와 함께 응답
     */
    @PostMapping
    public ResponseEntity<StudyCreateDTO> createStudy(@RequestBody StudyCreateDTO studyCreateDTO) {
        StudyCreateDTO createdStudy = studyService.createStudy(studyCreateDTO);
        return ResponseEntity.created(URI.create(createdStudy.getStudyUrl())).body(createdStudy);
    }

    /**
     * 스터디 삭제
     * @param id 삭제할 스터디의 ID
     * @return No Content 상태의 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long id) {
        studyService.deleteStudy(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 스터디 정보 업데이트
     * @param id 업데이트할 스터디의 ID
     * @param studyCreateDTO 업데이트할 스터디의 정 보를 담은 DTO
     * @return 업데이트된 스터디 정보를 담은 DTO와 함께 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudyCreateDTO> updateStudy(@PathVariable Long id, @RequestBody StudyCreateDTO studyCreateDTO) {
        StudyCreateDTO updatedStudy = studyService.updateStudy(id, studyCreateDTO);
        return ResponseEntity.ok(updatedStudy);
    }

    /**
     * 특정 스터디 조회
     * @param studyPk 조회할 스터디의 PK
     * @param model 뷰에 전달할 모델 객체
     * @return 스터디 페이지 이름
     */
    @GetMapping("/{studyPk}")
    public String getStudy(@PathVariable Long studyPk, Model model) {
        String studyName = studyService.getStudyNameById(studyPk);
        model.addAttribute("studyPk", studyPk);
        model.addAttribute("studyName", studyName);
        return "studyPage";
    }

    /**
     * 예외 처리
     * @param e 처리할 예외
     * @return 예외 메시지를 담은 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("무슨 에러냐면 : " + e.getMessage());
    }

    /**
     * JSON 에러 처리
     * @param ex 처리할 예외
     * @return 예외 메시지를 담은 응답
     */
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<String> handleJsonMappingException(JsonMappingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Json 관련 에러 발생 : " + ex.getMessage());
    }
}
