package com.als.SMore.study.management.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.study.dashboard.DTO.StudyMemberDTO;
import com.als.SMore.study.management.DTO.StudyMemberWithOutAdminDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudyMemberMapper {
    private static final Logger logger = LoggerFactory.getLogger(StudyMemberMapper.class);

    public static StudyMemberDTO toDTO(StudyMember studyMember) {
        String nickname = studyMember.getMember().getNickName();
        logger.info("nickname : {} 를 퇴출하셨습니다.", nickname);

        return StudyMemberDTO.builder()
                .memberPk(studyMember.getMember().getMemberPk())
                .nickName(nickname)
                .enterDate(studyMember.getEnterDate())
                .build();
    }

    public static StudyMemberWithOutAdminDTO toDTOWithoutAdmin(StudyMember studyMember) {
        Member member = studyMember.getMember();
        return StudyMemberWithOutAdminDTO.builder()
                .memberPk(member.getMemberPk())
                .nickname(member.getNickName())
                .imageURL(member.getProfileImg())
                .enterDate(studyMember.getEnterDate())
                .build();
    }
}
