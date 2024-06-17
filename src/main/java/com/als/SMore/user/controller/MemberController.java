package com.als.SMore.user.controller;

import com.als.SMore.user.dto.member.request.NicknameRequest;
import com.als.SMore.user.dto.member.request.ProfileImgRequest;
import com.als.SMore.user.dto.member.response.MemberProfileResponse;
import com.als.SMore.user.dto.member.response.NicknameResponse;
import com.als.SMore.user.dto.member.response.ProfileImgResponse;
import com.als.SMore.user.service.AwsFileService;
import com.als.SMore.user.service.MemberService;
import com.als.SMore.user.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;
    private final AwsFileService awsFileService;

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
    public ResponseEntity<ProfileImgResponse> modifyProfileImage(@RequestPart MultipartFile profileImage){
        String img = null;
        try {
            img = awsFileService.saveFile(profileImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(ProfileImgResponse.builder().profileImage(img).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable(name = "id") Long userId){
        return ResponseEntity.ok("굿");
    }
}
