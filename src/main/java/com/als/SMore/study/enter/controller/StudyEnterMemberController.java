package com.als.SMore.study.enter.controller;

import com.als.SMore.study.enter.DTO.StudyEnterMemberDTO;
import com.als.SMore.study.enter.service.StudyEnterMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{studyPk}/enter")
@RequiredArgsConstructor
public class StudyEnterMemberController {

    private final StudyEnterMemberService studyEnterMemberService;

    /**
     * 새로운 스터디 가입 신청서를 생성합니다.
     * @param studyEnterMemberDTO 생성할 StudyEnterMemberDTO 객체
     * @return 생성된 StudyEnterMemberDTO 객체와 함께 Created 응답 반환
     */
    @PostMapping
    public ResponseEntity<StudyEnterMemberDTO> createStudyEnterMember(
            @RequestBody StudyEnterMemberDTO studyEnterMemberDTO) {
        StudyEnterMemberDTO createdMember = studyEnterMemberService.createStudyEnterMember(studyEnterMemberDTO);
        return ResponseEntity.status(201).body(createdMember);
    }

    /**
     * 모든 스터디 가입 신청서를 조회합니다.
     * @return 모든 StudyEnterMemberDTO 리스트와 함께 OK 응답 반환
     */
    @GetMapping
    public ResponseEntity<List<StudyEnterMemberDTO>> getAllStudyEnterMembers() {
        List<StudyEnterMemberDTO> members = studyEnterMemberService.getAllStudyEnterMembers();
        return ResponseEntity.ok(members);
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
}
