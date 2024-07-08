package com.als.SMore.study.management.DTO;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyDataDTO {
//    @JsonSerialize(using = LongToStringSerializer.class)
//    @JsonDeserialize(using = StringToLongDeserializer.class)
//    private Long studyPk;

    private String studyName;
    private String imageUri;
    private int maxPeople;
    private String content;
    private LocalDate startDate;
    private LocalDate closeDate;

//    // 스터디 보드
//    @JsonSerialize(using = LongToStringSerializer.class)
//    @JsonDeserialize(using = StringToLongDeserializer.class)
//    private Long StudyBoardPk;

    private String adTitle;
    private String adContent;
}