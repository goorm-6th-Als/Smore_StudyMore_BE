package com.als.SMore.study.studyCRUD.DTO;

import com.als.SMore.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyDTO {
    private Long studyPk;
    private String studyName;
    private Member memberPk;
}
