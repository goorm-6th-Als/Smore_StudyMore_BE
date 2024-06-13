package com.als.SMore.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class MemberController {

    @GetMapping("/mypage")
    public ResponseEntity<String> getMyProfile(){
        return ResponseEntity.ok("굿");
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
