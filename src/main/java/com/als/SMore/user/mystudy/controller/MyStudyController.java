package com.als.SMore.user.mystudy.controller;

import com.als.SMore.user.mypage.dto.response.MessageResponse;
import com.als.SMore.user.mystudy.dto.request.IsCheckedStatusRequest;
import com.als.SMore.user.mystudy.dto.response.EnterStudyResponse;
import com.als.SMore.user.mystudy.dto.response.StudyListResponse;
import com.als.SMore.user.mystudy.service.MyStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/mystudy")
@RestController
public class MyStudyController {
    private final MyStudyService myStudyService;

    // 내가 참여하는 스터디 get
    @GetMapping()
    public ResponseEntity<StudyListResponse> enterStudy(){
        StudyListResponse studyListResponse = myStudyService.enterStudy();
        return ResponseEntity.ok(studyListResponse);
    }

    // 내가 운영하는 스터디 get
    @GetMapping("/admin")
    public ResponseEntity<StudyListResponse> operatedStudy(){
        StudyListResponse studyListResponse = myStudyService.operatedStudy();
        return ResponseEntity.ok(studyListResponse);
    }

    // 내가 운영하는 스터디에 신청한 사람들의 목록 get
    @GetMapping("/apply/{studyPk}")
    public ResponseEntity<EnterStudyResponse> applyMemberByStudy(@PathVariable(name = "studyPk") Long studyPk){
        EnterStudyResponse enterStudyResponse = myStudyService.applyMemberByStudy(studyPk);
        return ResponseEntity.ok(enterStudyResponse);
    }
    // 신청한 사람들을 승낙하는 기능 post
    @PostMapping("/apply")
    public ResponseEntity<MessageResponse> acceptMember(@RequestBody IsCheckedStatusRequest statusRequest){
        if(!myStudyService.acceptMember(statusRequest)){
            return ResponseEntity.ok(MessageResponse.builder().message("승인 실패").build());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.builder().message("승인 성공").build());
    }
    // 신청한 사람들을 거절하는 기능 post
    @PostMapping("/refuse")
    public ResponseEntity<MessageResponse> refuseMember(@RequestBody IsCheckedStatusRequest statusRequest){
        myStudyService.refuseMember(statusRequest);
        return ResponseEntity.ok(MessageResponse.builder().build());
    }
    // 참여하는 스터디를 탈퇴하는 기능 delete
    @DeleteMapping("/{studyPk}")
    public ResponseEntity<MessageResponse> resignMemberByStudy(@PathVariable(name = "studyPk") Long studyPk) {
        myStudyService.resignMemberByStudy(studyPk);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(MessageResponse.builder().build());
    }
}
