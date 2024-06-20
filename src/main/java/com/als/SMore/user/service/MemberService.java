package com.als.SMore.user.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.user.dto.member.request.NicknameRequest;
import com.als.SMore.user.dto.member.response.MessageResponse;
import com.als.SMore.user.dto.member.response.NicknameResponse;
import com.als.SMore.user.util.MemberUtil;
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
