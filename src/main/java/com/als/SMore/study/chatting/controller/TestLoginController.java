package com.als.SMore.study.chatting.controller;


import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.user.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/test")
public class TestLoginController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/login") // 로컬 테스트용 가짜 로그인 컨트롤러.
    public ResponseEntity<Void> login(@RequestBody Map<String, String> login) {
        String userId = login.get("userId");
        Member member = memberRepository.findByUserId(userId).orElseThrow(NoSuchElementException::new);

        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        String accessToken = tokenProvider.generateAccessToken(member.getMemberPk());
        String refreshToken = tokenProvider.generateRefreshToken(member.getMemberPk());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add("Refresh-Token", refreshToken);

        return ResponseEntity.ok().headers(headers).build();
    }
}