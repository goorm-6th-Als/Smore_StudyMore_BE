package com.als.SMore.study.studyCRUD.DTO;

import com.als.SMore.domain.entity.StudyBoard;
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

    private String adTitle;
    private String adContent;
    private String adSummary;
    private LocalDate modifyDate;

    public static StudyBoardDTO fromEntity(StudyBoard studyBoard, boolean includeContent) {
        return StudyBoardDTO.builder()
                .studyBoardPk(studyBoard.getStudyBoardPk())
                .studyPk(studyBoard.getStudy().getStudyPk())
                .adTitle(studyBoard.getAdTitle())
                .modifyDate(studyBoard.getModifyDate())
                .adContent(includeContent ? studyBoard.getAdContent() : null)
                .adSummary(includeContent ? null : studyBoard.getAdSummary())
                .build();
    }
}
