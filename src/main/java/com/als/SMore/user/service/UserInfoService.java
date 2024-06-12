package com.als.SMore.user.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.user.dto.UserInfo;
import com.als.SMore.user.dto.UserInfoDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final MemberRepository memberRepository;

    public OAuth2User loadUserByUserPk(Long id) {
        Member member = memberRepository.findById(id).get();
        UserInfoDetails user = UserInfoDetails.builder()
                .userId(member.getUserId())
                .userPk(member.getMemberPk())
                .fullName(member.getFullName())
                .nickName(member.getNickName())
                .profileImg(member.getProfileImg()).build();

        return new UserInfo(user);
    }
}
