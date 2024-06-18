package com.als.SMore.study.management.controller;

import com.als.SMore.study.management.DTO.StudyUpdateDTO;
import com.als.SMore.study.management.service.StudyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/management")
@RequiredArgsConstructor
public class StudyManagementController {

    private final StudyManagementService studyManagementService;


    /**
     * 스터디 정보 업데이트
     * @param studyPk 업데이트할 스터디의 PK
     * @param studyUpdateDTO 업데이트할 스터디 정보를 담은 DTO
     * @return 업데이트된 스터디 정보를 담은 DTO와 함께 응답
     */
    @PutMapping("/{studyPk}")
    public ResponseEntity<StudyUpdateDTO> updateStudy(
            @PathVariable Long studyPk,
            @RequestBody StudyUpdateDTO studyUpdateDTO) {
        StudyUpdateDTO updatedStudy = studyManagementService.updateStudy(studyPk, studyUpdateDTO);
        return ResponseEntity.ok(updatedStudy);
    }

    /**
     * 스터디 삭제
     * @param id 삭제할 스터디의 ID
     * @return No Content 상태의 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long id) {
        studyManagementService.deleteStudy(id);
        return ResponseEntity.noContent().build();
    }
}
