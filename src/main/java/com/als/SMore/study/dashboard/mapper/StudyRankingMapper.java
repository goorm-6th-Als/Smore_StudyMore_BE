package com.als.SMore.study.dashboard.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.study.dashboard.DTO.StudyRankingDTO;

public class StudyRankingMapper {
    public static StudyRankingDTO toDTO(Member member, Long learningTime) {
        return StudyRankingDTO.builder()
                .memberPk(member.getMemberPk())
                .fullName(member.getFullName())
                .learningTime(learningTime)
                .build();
    }
}
