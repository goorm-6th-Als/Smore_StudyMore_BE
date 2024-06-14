package com.als.SMore.user.controller;

import com.als.SMore.user.dto.member.request.NicknameRequest;
import com.als.SMore.user.dto.member.response.MemberProfileResponse;
import com.als.SMore.user.dto.member.response.NicknameResponse;
import com.als.SMore.user.service.MemberService;
import com.als.SMore.user.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/mypage")
    public ResponseEntity<MemberProfileResponse> getMyProfile(){
        return ResponseEntity.ok(MemberProfileResponse.builder()
                .nickname(MemberUtil.getNickname())
                .profileImage(MemberUtil.getProfileUrl()).build());
    }

    @PatchMapping("/nickname")
    public ResponseEntity<NicknameResponse> modifyNickname(@RequestBody NicknameRequest nickname){
        NicknameResponse nicknameResponse = memberService.modifyNickname(nickname);
        return ResponseEntity.ok(nicknameResponse);
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
