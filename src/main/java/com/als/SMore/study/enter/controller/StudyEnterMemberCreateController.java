package com.als.SMore.study.enter.controller;

import com.als.SMore.study.enter.DTO.StudyEnterMemberDTO;
import com.als.SMore.study.enter.service.StudyEnterMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/{studyPk}/enter")
@RequiredArgsConstructor
public class StudyEnterMemberCreateController {

    private final StudyEnterMemberService studyEnterMemberService;

    /**
     * 스터디 가입 신청서를 생성
     * @param studyEnterMemberDTO 생성할 StudyEnterMemberDTO 객체
     * @param studyPk 스터디 게시물 PK
     * @return 생성된 StudyEnterMemberDTO 객체와 함께 Created 응답 반환
     */
    @PostMapping
    public ResponseEntity<StudyEnterMemberDTO> createStudyEnterMember(
            @RequestBody StudyEnterMemberDTO studyEnterMemberDTO,
            @PathVariable Long studyPk) {
        StudyEnterMemberDTO createdStudyEnter = studyEnterMemberService.createStudyEnterMember(studyEnterMemberDTO, studyPk);
        return ResponseEntity.status(201).body(createdStudyEnter);
    }
}
