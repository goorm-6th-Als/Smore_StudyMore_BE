package com.als.SMore.user.controller;

import com.als.SMore.user.dto.member.response.MemberProfileResponse;
import com.als.SMore.user.service.UserInfoService;
import com.als.SMore.user.util.MemberUtil;
import org.aspectj.weaver.MemberUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class MemberController {

    @GetMapping("/mypage")
    public ResponseEntity<MemberProfileResponse> getMyProfile(){
        return ResponseEntity.ok(MemberProfileResponse.builder()
                .nickname(MemberUtil.getNickname())
                .profileImage(MemberUtil.getProfileUrl()).build());
    }

    @PatchMapping("/nickname")
    public ResponseEntity<String> modifyNickname(){
        return ResponseEntity.ok("굿");
    }

    @PatchMapping("/profileImage")
    public ResponseEntity<String> modifyProfileImage(){
        return ResponseEntity.ok("굿");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable(name = "id") Long userId){
        return ResponseEntity.ok("굿");
    }
}
