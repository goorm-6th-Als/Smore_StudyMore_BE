package com.als.SMore.study.dashboard.DTO;


import com.als.SMore.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyRankingDTO {
    private Long memberPk;
    private String fullName;
    private Long learningTime; // Time in seconds

    public static StudyRankingDTO fromEntity(Member member, Long learningTime) {
        return StudyRankingDTO.builder()
                .memberPk(member.getMemberPk())
                .fullName(member.getFullName())
                .learningTime(learningTime)
                .build();
    }
}