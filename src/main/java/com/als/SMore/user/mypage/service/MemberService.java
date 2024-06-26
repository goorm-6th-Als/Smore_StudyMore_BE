package com.als.SMore.user.mypage.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.user.mypage.dto.request.NicknameRequest;
import com.als.SMore.user.mypage.dto.response.NicknameResponse;
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public NicknameResponse modifyNickname(NicknameRequest nickname){
        Long userPk = MemberUtil.getUserPk();
        Member member = memberRepository.findById(userPk).get();
        Member updatedMember = member.toBuilder().nickName(nickname.getNickname()).build();
        memberRepository.save(updatedMember);

        return NicknameResponse.builder().nickname(updatedMember.getNickName()).build();
    }

}
