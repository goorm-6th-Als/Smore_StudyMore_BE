package com.als.SMore.user.mystudy.dto.response;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.global.json.LongToStringSerializer;
import com.als.SMore.global.json.StringToLongDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudyResponse {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long studyPk;
    private String studyName;
    private String studyImg;
    private int maxPeople;
    private Long studyPersonNum;
    private LocalDate studyStartDate;
    private LocalDate studyEndDate;

    public StudyResponse(Study study, StudyDetail studyDetail, Long studyPersonNum){
        this.studyPk = study.getStudyPk();
        this.studyName = study.getStudyName();
        this.studyImg = studyDetail.getImageUri();
        this.studyStartDate = studyDetail.getStartDate();
        this.studyEndDate = studyDetail.getCloseDate();
        this.maxPeople = studyDetail.getMaxPeople();
        this.studyPersonNum = studyPersonNum;
    }

    public static StudyResponse from(Study study, StudyDetail studyDetail, Long studyPersonNum){
        return new StudyResponse(study,studyDetail,studyPersonNum);
    }

}
