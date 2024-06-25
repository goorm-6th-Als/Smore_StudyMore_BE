package com.als.SMore.study.dashboard.DTO;


import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.StudyMember;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyMemberDTO {
    private Long memberPk;
    private String nickName;
    private String role;
    private LocalDate enterDate;

    public static StudyMemberDTO fromEntity(StudyMember studyMember) {
        Member member = studyMember.getMember();
        return StudyMemberDTO.builder()
                .memberPk(member.getMemberPk())
                .nickName(member.getNickName())
                .role(studyMember.getRole())
                .enterDate(studyMember.getEnterDate())
                .build();
    }
}