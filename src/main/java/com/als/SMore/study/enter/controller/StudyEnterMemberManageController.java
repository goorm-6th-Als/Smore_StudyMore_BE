package com.als.SMore.study.enter.controller;

import com.als.SMore.study.enter.DTO.StudyEnterMemberDTO;
import com.als.SMore.study.enter.DTO.StudyEnterMemberWithStudyInfoDTO;
import com.als.SMore.study.enter.service.StudyEnterMemberService;
import com.als.SMore.user.login.util.NotAop;
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

//    /**
//     * 특정 스터디의 모든 가입 신청서 조회
//     * @param studyPk 스터디 PK
//     * @return 해당 스터디의 모든 신청서 리스트와 Ok 반환
//     */
//
//    @GetMapping("/{studyPk}")
//    public ResponseEntity<List<StudyEnterMemberWithMemberInfoDTO>> getAllStudyEnterMembers(
//            @PathVariable Long studyPk) {
//        List<StudyEnterMemberWithMemberInfoDTO> studyEnterMembers = studyEnterMemberService.getAllStudyEnterMembers(studyPk);
//        return ResponseEntity.ok(studyEnterMembers);
//    }

    /**
     * 나의 모든 가입 신청서 조회
     * @return 해당 멤버의 모든 신청서 리스트와 Ok 반환
     */
    @NotAop
    @GetMapping
    public ResponseEntity<List<StudyEnterMemberWithStudyInfoDTO>> getAllStudyEnterMembersByMember(){
        List<StudyEnterMemberWithStudyInfoDTO> studyEnterMembers = studyEnterMemberService.getAllStudyEnterMembersByMember();
        return ResponseEntity.ok(studyEnterMembers);
    }

    /**
     * 스터디 가입 신청서 삭제
     * @param studyEnterMemberPk 삭제할 StudyEnterMember의 PK
     * @return No Content 응답 반환
     */

    @NotAop
    @DeleteMapping("/{studyEnterMemberPk}")
    public ResponseEntity<Void> deleteStudyEnterMember(@PathVariable Long studyEnterMemberPk) {
        studyEnterMemberService.deleteStudyEnterMember(studyEnterMemberPk);
        return ResponseEntity.noContent().build();
    }

    /**
     * 스터디 가입 신청서 수정
     * @param studyEnterMemberPk 수정할 StudyEnterMember의 PK
     * @param studyEnterMemberDTO 수정할 StudyEnterMemberDTO 객체
     * @return 수정된 StudyEnterMemberDTO 객체와 함께 OK 응답 반환
     */

    @NotAop
    @PutMapping("/{studyEnterMemberPk}")
    public ResponseEntity<StudyEnterMemberDTO> updateStudyEnterMember(
            @PathVariable Long studyEnterMemberPk,
            @RequestBody StudyEnterMemberDTO studyEnterMemberDTO) {
        StudyEnterMemberDTO updatedMember = studyEnterMemberService.updateStudyEnterMember(studyEnterMemberPk, studyEnterMemberDTO);
        return ResponseEntity.ok(updatedMember);
    }
}