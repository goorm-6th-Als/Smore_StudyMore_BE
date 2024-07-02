package com.als.SMore.user.login.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.MemberToken;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.MemberTokenRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.user.login.dto.UserInfo;
import com.als.SMore.user.login.dto.UserInfoDetails;
import com.als.SMore.user.login.dto.response.TokenResponse;
import com.als.SMore.user.login.util.MemberUtil;
import com.als.SMore.user.login.util.TokenProvider;
import com.als.SMore.user.mypage.dto.response.MessageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final MemberRepository memberRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final TokenProvider tokenProvider;
    private final MemberTokenRepository memberTokenRepository;

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

    @Transactional
    public TokenResponse creatRenewToken(){
        Long userPk = MemberUtil.getUserPk();
        // 스터디에 가입된 정보를 조회함
        List<StudyMember> studyMemberList = studyMemberRepository.findByMember_MemberPk(userPk);
        MemberToken memberToken = memberTokenRepository.findMemberTokenByMember_MemberPk(userPk)
                .orElseThrow(IllegalArgumentException::new);

        Map<String,String> role = new HashMap<>();
        if(studyMemberList.isEmpty()){
            String accessToken = tokenProvider.generateAccessToken(userPk, role);
            String refreshToken = tokenProvider.generateRefreshToken(userPk, role);
            memberToken = memberToken.toBuilder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken).build();
            memberTokenRepository.save(memberToken);
            TokenResponse tokenResponse = TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken).build();
            return tokenResponse;
        }

        // 조회된 정보에서 스터디의 역할을 받아온
        for (StudyMember studyMember : studyMemberList) {
            role.put(String.valueOf(studyMember.getMember().getMemberPk()),studyMember.getRole());
        }

        String accessToken = tokenProvider.generateAccessToken(userPk, role);
        String refreshToken = tokenProvider.generateRefreshToken(userPk, role);
        memberToken = memberToken.toBuilder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
        memberTokenRepository.save(memberToken);
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
        return tokenResponse;
    }

    public MessageResponse logOut(){
        Long userPk = MemberUtil.getUserPk();
        MemberToken memberToken = memberTokenRepository.findMemberTokenByMember_MemberPk(userPk)
                .orElseThrow(IllegalArgumentException::new);

        memberTokenRepository.delete(memberToken);
        return MessageResponse.builder()
                .message("로그아웃 되었습니다")
                .build();
    }
}
