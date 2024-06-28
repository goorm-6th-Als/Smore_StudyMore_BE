package com.als.SMore.user.mypage.controller;

import com.als.SMore.user.mypage.dto.request.NicknameRequest;
import com.als.SMore.user.mypage.dto.response.MemberProfileResponse;
import com.als.SMore.user.mypage.dto.response.MessageResponse;
import com.als.SMore.user.mypage.dto.response.NicknameResponse;
import com.als.SMore.user.mypage.dto.response.ProfileImgResponse;
import com.als.SMore.user.mypage.service.AwsFileService;
import com.als.SMore.user.mypage.service.MemberService;
import com.als.SMore.user.login.util.MemberUtil;
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

    @DeleteMapping()
    public ResponseEntity<MessageResponse> deleteMember(){
        MessageResponse messageResponse = memberService.deleteMember();
        return ResponseEntity.ok(messageResponse);
    }
}
