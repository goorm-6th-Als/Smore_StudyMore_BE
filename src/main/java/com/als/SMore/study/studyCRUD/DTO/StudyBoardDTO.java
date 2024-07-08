package com.als.SMore.study.studyCRUD.DTO;

import com.als.SMore.global.json.LongToStringSerializer;
import com.als.SMore.global.json.StringToLongDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyBoardDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long studyBoardPk;

    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long studyPk;

    private String studyName;

    private String adTitle;
    private String adContent;
    private String imageUri;

    private long curPeople;
    private int maxPeople;
    private LocalDate startDate;
    private LocalDate closeDate;
    private LocalDate modifyDate;
}
