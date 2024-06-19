package com.als.SMore.user.controller;

import com.als.SMore.user.dto.member.response.MessageResponse;
import com.als.SMore.user.dto.mystudy.request.IsCheckedStatusRequest;
import com.als.SMore.user.dto.mystudy.response.EnterStudyResponse;
import com.als.SMore.user.dto.mystudy.response.StudyListResponse;
import com.als.SMore.user.service.MyStudyService;
import com.als.SMore.user.util.MemberUtil;
import com.amazonaws.Response;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/study")
@RestController
public class MyStudyController {
    private final MyStudyService myStudyService;

    // 내가 참여하는 스터디 get
    @GetMapping()
    public ResponseEntity<StudyListResponse> enterStudy(){
        return ResponseEntity.ok(StudyListResponse.builder().build());
    }

    // 내가 운영하는 스터디 get
    @GetMapping("/admin")
    public ResponseEntity<StudyListResponse> operatedStudy(){
        return ResponseEntity.ok(StudyListResponse.builder().build());
    }

    // 내가 운영하는 스터디에 신청한 사람들의 목록 get
    @GetMapping("/apply/{studyPk}")
    public ResponseEntity<EnterStudyResponse> applyMemberByStudy(@PathVariable(name = "studyPk") Long studyPk){
        return ResponseEntity.ok(EnterStudyResponse.builder().build());
    }
    // 신청한 사람들을 승낙하는 기능 post
    @PostMapping("/apply")
    public ResponseEntity<MessageResponse> acceptMember(@RequestBody IsCheckedStatusRequest statusRequest){
        myStudyService.acceptMember(statusRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.builder().build());
    }
    // 신청한 사람들을 거절하는 기능 post
    @PostMapping("/refuse")
    public ResponseEntity<MessageResponse> refuseMember(@RequestBody IsCheckedStatusRequest statusRequest){
        return ResponseEntity.ok(MessageResponse.builder().build());
    }
    // 참여하는 스터디를 탈퇴하는 기능 delete
    @DeleteMapping("/{studyPk}")
    public ResponseEntity<MessageResponse> resignMemberByStudy(@PathVariable(name = "studyPk") Long studyPk) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(MessageResponse.builder().build());
    }
}
