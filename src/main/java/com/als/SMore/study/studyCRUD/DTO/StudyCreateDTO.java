package com.als.SMore.study.studyCRUD.DTO;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.global.json.LongToStringSerializer;
import com.als.SMore.global.json.StringToLongDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyCreateDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long studyPk;

    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long memberPk;

    private String studyName;
    private String imageUri;
    private int maxPeople;
    private String content;
    private LocalDate startDate;
    private LocalDate closeDate;

    public static StudyCreateDTO fromEntity(Study study, StudyDetail studyDetail) {
        return StudyCreateDTO.builder()
                .studyPk(study.getStudyPk())
                .memberPk(study.getMember().getMemberPk())
                .studyName(study.getStudyName())
                .imageUri(studyDetail.getImageUri())
                .maxPeople(studyDetail.getMaxPeople())
                .content(studyDetail.getContent())
                .startDate(studyDetail.getStartDate())
                .closeDate(studyDetail.getCloseDate())
                .build();
    }
}
