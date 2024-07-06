package com.als.SMore.study.management.DTO;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyMemberWithOutAdminDTO {
    private Long memberPk;
    private String nickname;
    private String imageURL;
    private LocalDate enterDate;
}
