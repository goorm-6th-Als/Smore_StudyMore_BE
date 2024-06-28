package com.als.SMore.study.management.controller;

import com.als.SMore.study.management.DTO.StudyUpdateDTO;
import com.als.SMore.study.management.service.StudyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * 스터디 멤버가 존재하지 않아야 함.
     * @param studyPk 삭제할 스터디의 PK
     * @return No Content 상태의 응답
     */
    @DeleteMapping("/{studyPk}")
    public ResponseEntity<Void> deleteStudy(
            @PathVariable Long studyPk) {
        studyManagementService.deleteStudy(studyPk);
        return ResponseEntity.noContent().build();
    }
}
