package com.als.SMore.study.studyCRUD.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.study.studyCRUD.DTO.StudyCreateDTO;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudyCreateMapper {
    private static final Logger logger = LoggerFactory.getLogger(StudyCreateMapper.class);

    public static Study toStudy(StudyCreateDTO dto, Member member) {
//        logger.info("Study 엔티티 생성");
        return Study.builder()
                .studyName(dto.getStudyName())
                .member(member)
                .build();
    }

    public static StudyDetail toStudyDetail(StudyCreateDTO dto, Study study) {
//        logger.info("StudyDetail 엔티티 생성");
        return StudyDetail.builder()
                .study(study)
                .imageUri(dto.getImageUri())
                .maxPeople(dto.getMaxPeople())
                .content(dto.getContent())
                .startDate(dto.getStartDate())
                .closeDate(dto.getCloseDate())
                .build();
    }

    public static StudyMember toStudyMember(StudyCreateDTO dto, Study study, Member member) {
//        logger.info("StudyMember 엔티티 생성");
        return StudyMember.builder()
                .study(study)
                .member(member)
                .role("admin")
                .enterDate(LocalDate.now())
                .build();
    }

    public static StudyBoard toStudyBoard(StudyCreateDTO dto, Study study) {
//        logger.info("StudyBoard 엔티티 생성");
        String adSummary = dto.getContent().length() > 30
                ? dto.getContent().substring(0, 30) + "..."
                : dto.getContent();

        return StudyBoard.builder()
                .study(study)
                .adTitle(dto.getStudyName())
                .adContent(dto.getContent())
                .adSummary(adSummary)
                .modifyDate(LocalDate.now())
                .imageUri(dto.getImageUri())
                .build();
    }
}
