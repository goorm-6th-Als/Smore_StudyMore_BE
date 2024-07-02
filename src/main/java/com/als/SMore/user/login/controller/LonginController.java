package com.als.SMore.user.login.controller;

import com.als.SMore.user.login.dto.response.TokenResponse;
import com.als.SMore.user.login.service.UserInfoService;
import com.als.SMore.user.mypage.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LonginController {

    private final UserInfoService userInfoService;

    /**
     * 만료된 토큰을 재발급 API
     * @return MessageResponse
     */
    @PostMapping("/login/re-token")
    public ResponseEntity<MessageResponse> createRenewToken(){
        TokenResponse tokenResponse = userInfoService.creatRenewToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,tokenResponse.getAccessToken());
        httpHeaders.set("X-Refresh-Token",tokenResponse.getRefreshToken());
        MessageResponse message = MessageResponse.builder()
                .message("토큰이 제발급되었습니다")
                .build();

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(message);
    }
}
