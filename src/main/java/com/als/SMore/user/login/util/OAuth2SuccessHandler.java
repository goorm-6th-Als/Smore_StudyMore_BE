package com.als.SMore.user.login.util;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.user.login.dto.KakaoUserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String REDIRECT_URI = "http://localhost:3000/login/kakao/%s/%s";
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        Member member = null;
        try {
            member = memberRepository.findByUserId(kakaoUserInfo.getEmail())
                    .orElseThrow(IllegalAccessException::new);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        String accessToken = tokenProvider.generateAccessToken(member.getMemberPk());
        String refreshToken = tokenProvider.generateRefreshToken(member.getMemberPk());

        String redirectURI = String.format(REDIRECT_URI, accessToken, refreshToken);
        getRedirectStrategy().sendRedirect(request,response,redirectURI);
    }
}
