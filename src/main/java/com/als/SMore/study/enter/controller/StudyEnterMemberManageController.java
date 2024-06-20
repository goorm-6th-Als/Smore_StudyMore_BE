package com.als.SMore.study.enter.controller;

import com.als.SMore.study.enter.DTO.StudyEnterMemberDTO;
import com.als.SMore.study.enter.service.StudyEnterMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mystudy/apply")
@RequiredArgsConstructor
public class StudyEnterMemberManageController {

    private final StudyEnterMemberService studyEnterMemberService;


    /**
     * 특정 스터디의 모든 가입 신청서 조회
     * @param studyPk 스터디 PK
     * @return 모든 StudyEnterMemberDTO 리스트와 함께 OK 응답 반환
     */
    @GetMapping("/study/{studyPk}")
    public ResponseEntity<List<StudyEnterMemberDTO>> getAllStudyEnterMembers(@PathVariable Long studyPk) {
        List<StudyEnterMemberDTO> studyEnterMembers = studyEnterMemberService.getAllStudyEnterMembers(studyPk);
        return ResponseEntity.ok(studyEnterMembers);
    }

    /**
     * 특정 멤버의 모든 가입 신청서 조회
     * @param memberPk 멤버 PK
     * @return 해당 멤버의 모든 StudyEnterMemberDTO 리스트와 함께 OK 응답 반환
     */
    @GetMapping("/member/{memberPk}")
    public ResponseEntity<List<StudyEnterMemberDTO>> getAllStudyEnterMembersByMember(@PathVariable Long memberPk) {
        List<StudyEnterMemberDTO> studyEnterMembers = studyEnterMemberService.getAllStudyEnterMembersByMember(memberPk);
        return ResponseEntity.ok(studyEnterMembers);
    }

    /**
     * 스터디 가입 신청서를 삭제합니다.
     * @param studyEnterMemberPk 삭제할 StudyEnterMember의 PK
     * @return No Content 응답 반환
     */
    @DeleteMapping("/{studyEnterMemberPk}")
    public ResponseEntity<Void> deleteStudyEnterMember(@PathVariable Long studyEnterMemberPk) {
        studyEnterMemberService.deleteStudyEnterMember(studyEnterMemberPk);
        return ResponseEntity.noContent().build();
    }

    /**
     * 스터디 가입 신청서의 내용을 수정합니다.
     * @param studyEnterMemberPk 수정할 StudyEnterMember의 PK
     * @param studyEnterMemberDTO 수정할 StudyEnterMemberDTO 객체
     * @return 수정된 StudyEnterMemberDTO 객체와 함께 OK 응답 반환
     */
    @PutMapping("/{studyEnterMemberPk}")
    public ResponseEntity<StudyEnterMemberDTO> updateStudyEnterMember(
            @PathVariable Long studyEnterMemberPk, @RequestBody StudyEnterMemberDTO studyEnterMemberDTO) {
        StudyEnterMemberDTO updatedMember = studyEnterMemberService.updateStudyEnterMember(studyEnterMemberPk, studyEnterMemberDTO);
        return ResponseEntity.ok(updatedMember);
    }
}