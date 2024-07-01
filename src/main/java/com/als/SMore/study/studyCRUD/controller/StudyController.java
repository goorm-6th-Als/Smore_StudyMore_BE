package com.als.SMore.study.studyCRUD.controller;

import com.als.SMore.study.studyCRUD.DTO.StudyCreateDTO;
import com.als.SMore.study.studyCRUD.service.StudyService;
import com.als.SMore.user.mypage.service.AwsFileService;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final AwsFileService awsFileService;

    /**
     * 스터디 대표 이미지 생성
     * @param studyImage 스터디의 대표 이미지
     * @return 생성된 스터디의 이미지 주소를 반환
     */
    @PostMapping("/image")
    public ResponseEntity<String> createStudyImage(@RequestPart MultipartFile studyImage){
        String studyImageUri = awsFileService.saveStudyFile(studyImage);
        return ResponseEntity.ok(studyImageUri);
    }

    /**
     * 스터디 생성
     * @param studyCreateDTO 생성할 스터디의 정보를 담은 DTO
     * @return 생성된 스터디 정보를 담은 DTO와 함께 응답
     */
    @PostMapping
    public ResponseEntity<StudyCreateDTO> createStudy(@RequestBody StudyCreateDTO studyCreateDTO) {
        StudyCreateDTO createdStudy = studyService.createStudy(studyCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudy);
    }

    /**
     * 특정 스터디 조회
     * @param studyPk 조회할 스터디의 PK
     * @return 스터디 이름과 함께 응답
     */
    @GetMapping("/{studyPk}")
    public ResponseEntity<String> getStudy(@PathVariable Long studyPk) {
        String studyName = studyService.getStudyNameById(studyPk);
        return ResponseEntity.ok(studyName);
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
