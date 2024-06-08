package com.als.SMore.study.studyCRUD.DTO;

import com.als.SMore.domain.entity.StudyBoard;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyBoardDTO {
    private Long studyBoardPk;
    private Long studyPk;
    private String adTitle;
    private String adContent;
    private String adSummary;
    private LocalDate createDate;
    private LocalDate modifyDate;
    private LocalDate closeDate;

    // 기본 생성자
    public StudyBoardDTO() {}

    // 스터디 모집 게시물 생성자
    public StudyBoardDTO(StudyBoard studyBoard, boolean includeContent) {
        this.studyBoardPk = studyBoard.getStudyBoardPk();
        this.studyPk = studyBoard.getStudy().getStudyPk();
        this.adTitle = studyBoard.getAdTitle();
        this.createDate = studyBoard.getCreateDate();
        this.modifyDate = studyBoard.getModifyDate();
        this.closeDate = studyBoard.getCloseDate();
        if (includeContent) {
            this.adContent = studyBoard.getAdContent();
        } else {
            this.adSummary = studyBoard.getAdSummary();
        }
    }
}
