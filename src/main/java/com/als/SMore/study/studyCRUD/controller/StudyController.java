package com.als.SMore.study.studyCRUD.controller;

import com.als.SMore.study.studyCRUD.DTO.StudyCreateDTO;
import com.als.SMore.study.studyCRUD.service.StudyService;
import com.als.SMore.user.login.util.aop.annotation.NotAop;
import com.als.SMore.user.mypage.service.AwsFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final AwsFileService awsFileService;

    /**
     * 스터디 생성
     *
     * @param studyCreateDTO 생성할 스터디의 정보를 담은 DTO
     * @return 생성된 스터디 정보를 담은 DTO와 함께 응답
     */
    @NotAop
    @PostMapping
    public ResponseEntity<StudyCreateDTO> createStudy(
            @RequestPart("studyCreateDTO") StudyCreateDTO studyCreateDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) {
            StudyCreateDTO createdStudy = studyService.createStudy(studyCreateDTO, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudy);
    }

        /**
         * 특정 스터디 조회
         *
         * @param studyPk 조회할 스터디의 PK
         * @return 스터디 이름과 함께 응답
         */
        @GetMapping("/{studyPk}")
        public ResponseEntity<String> getStudy (@PathVariable Long studyPk){
            String studyName = studyService.getStudyNameById(studyPk);
            return ResponseEntity.ok(studyName);
    }
}
