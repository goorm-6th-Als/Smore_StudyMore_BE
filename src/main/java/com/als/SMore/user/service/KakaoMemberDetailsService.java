package com.als.SMore.user.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.user.dto.KakaoMemberDetails;
import com.als.SMore.user.dto.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoMemberDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        log.info("imageURL : {}",kakaoUserInfo.getProfileImgUrl());

        Member member = memberRepository.findByUserId(kakaoUserInfo.getEmail())
                .orElseGet(() ->
                        memberRepository.save(
                                Member.builder()
                                        .userId(kakaoUserInfo.getEmail())
                                        .profileImg(kakaoUserInfo.getProfileImgUrl())
                                        .fullName(kakaoUserInfo.getName())
                                        .nickName(kakaoUserInfo.getNickname())
                                        .build()
                        )
                );

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");

        return new KakaoMemberDetails(String.valueOf(member.getUserId()),
                Collections.singletonList(authority),
                oAuth2User.getAttributes());
    }
}
