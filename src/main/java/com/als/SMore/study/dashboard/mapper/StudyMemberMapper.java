package com.als.SMore.study.dashboard.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;

public class StudyMemberMapper {
    public static StudyMemberDTO toDTO(StudyMember studyMember) {
        Member member = studyMember.getMember();
        return StudyMemberDTO.builder()
                .memberPk(member.getMemberPk())
                .nickName(member.getNickName())
                .profileImg(member.getProfileImg())
                .role(studyMember.getRole())
                .enterDate(studyMember.getEnterDate())
                .build();
    }
}
