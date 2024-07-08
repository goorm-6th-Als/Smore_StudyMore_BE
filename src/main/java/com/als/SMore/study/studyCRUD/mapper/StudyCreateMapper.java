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
        String lengthLimitStudyName = dto.getStudyName().length() > 10
                ? dto.getStudyName().substring(0, 10)
                : dto.getStudyName();

        return Study.builder()
                .studyName(lengthLimitStudyName)
                .member(member)
                .build();
    }

    public static StudyDetail toStudyDetail(StudyCreateDTO dto, Study study, String imageUrl) {
        LocalDate startDate = dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now(); // 기본값으로 오늘 날짜 설정
        int maxPeople = dto.getMaxPeople();
        if (maxPeople < 2 || maxPeople > 6) {
            throw new IllegalArgumentException("최대인원은 2 ~ 6 범위를 입력해야 합니다.");
        }
        return StudyDetail.builder()
                .study(study)
                .imageUri(imageUrl) // 이미지 URL 설정
                .maxPeople(maxPeople)
                .content(dto.getContent())
                .startDate(startDate)
                .closeDate(dto.getCloseDate())
                .build();
    }

    public static StudyMember toStudyMember(StudyCreateDTO dto, Study study, Member member) {
        return StudyMember.builder()
                .study(study)
                .member(member)
                .role("admin")
                .enterDate(LocalDate.now())
                .build();
    }

    public static StudyBoard toStudyBoard(StudyCreateDTO dto, Study study) {
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
