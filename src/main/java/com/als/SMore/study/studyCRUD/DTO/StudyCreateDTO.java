package com.als.SMore.study.studyCRUD.DTO;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyMember;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyCreateDTO {
    private Long studyPk;
    private Long memberPk;
    private String studyName;
    private String imageUri;
    private int maxPeople;
    private String content;
    private LocalDate startDate;
    private LocalDate closeDate;

    public static StudyCreateDTO fromEntity(Study study, StudyDetail studyDetail ) {
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

    public static Study toEntity(StudyCreateDTO studyCreateDTO, Member member) {
        return Study.builder()
                .studyName(studyCreateDTO.getStudyName())
                .member(member)
                .build();
    }

    public static StudyDetail toDetailEntity(StudyCreateDTO studyCreateDTO, Study study) {
        return StudyDetail.builder()
                .study(study)
                .imageUri(studyCreateDTO.getImageUri())
                .maxPeople(studyCreateDTO.getMaxPeople())
                .content(studyCreateDTO.getContent())
                .startDate(studyCreateDTO.getStartDate())
                .closeDate(studyCreateDTO.getCloseDate())
                .build();
    }

    public static StudyMember toMemberEntity(StudyCreateDTO studyCreateDTO, Study study, Member member) {
        return StudyMember.builder()
                .study(study)
                .member(member)
                .role("admin")
                .enterDate(LocalDate.now())
                .build();
    }

    public static StudyBoard toBoardEntity(StudyCreateDTO studyCreateDTO, Study study) {
        String adSummary = studyCreateDTO.getContent().length() > 30 ? studyCreateDTO.getContent().substring(0, 30) + "..." : studyCreateDTO.getContent();

        return StudyBoard.builder()
                .study(study)
                .adTitle(studyCreateDTO.getStudyName())
                .adContent(studyCreateDTO.getContent())
                .adSummary(adSummary)
                .modifyDate(LocalDate.now())
                .imageUri(studyCreateDTO.getImageUri())
                .build();
    }


}
