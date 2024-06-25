package com.als.SMore.user.login.util;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.StudyEnterMember;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.MemberTokenRepository;
import com.als.SMore.domain.repository.StudyEnterMemberRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.user.login.dto.KakaoUserInfo;
import com.als.SMore.user.mystudy.dto.response.EnterStudy;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String REDIRECT_URI = "http://localhost:3000/redirection/%s/%s";
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        Member member = null;
        Map<String,String> claim = new HashMap<>();
        try {
            member = memberRepository.findByUserId(kakaoUserInfo.getEmail())
                    .orElseThrow(IllegalAccessException::new);
            List<StudyMember> studyMemberList = studyMemberRepository.findByMember_MemberPk(member.getMemberPk());
            if (!studyMemberList.isEmpty()){
                for (StudyMember studyMember : studyMemberList) {
                    String studyPk = String.valueOf(studyMember.getStudy().getStudyPk());
                    String role = studyMember.getRole();

                    claim.put(studyPk,role);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        String accessToken = tokenProvider.generateAccessToken(member.getMemberPk(),claim);
        String refreshToken = tokenProvider.generateRefreshToken(member.getMemberPk(),claim);

        String redirectURI = String.format(REDIRECT_URI, accessToken, refreshToken);
        getRedirectStrategy().sendRedirect(request,response,redirectURI);
    }
}
