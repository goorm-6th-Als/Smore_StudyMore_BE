package com.als.SMore.study.dashboard.DTO;


import com.als.SMore.domain.entity.Member;
import com.als.SMore.global.json.LongToStringSerializer;
import com.als.SMore.global.json.StringToLongDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyRankingDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
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