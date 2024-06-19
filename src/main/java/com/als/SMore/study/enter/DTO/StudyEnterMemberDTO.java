package com.als.SMore.study.enter.DTO;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyEnterMember;
import com.als.SMore.domain.entity.StudyEnterMemberStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class StudyEnterMemberDTO {
    private Long studyEnterMemberPk;
    private Long studyPk;
    private Long memberPk;
    private String content;
    private StudyEnterMemberStatus enterStatus;
    private LocalDateTime createDate;

    // Entity를 DTO로 변환
    public static StudyEnterMemberDTO fromEntity(StudyEnterMember studyEnterMember) {
        return StudyEnterMemberDTO.builder()
                .studyEnterMemberPk(studyEnterMember.getStudyEnterMemberPk())
                .studyPk(studyEnterMember.getStudy().getStudyPk())
                .memberPk(studyEnterMember.getMember().getMemberPk())
                .content(studyEnterMember.getContent())
                .enterStatus(studyEnterMember.getEnterStatus())
                .createDate(studyEnterMember.getCreateDate())
                .build();
    }

    // DTO를 Entity로 변환
    public static StudyEnterMember toEntity(StudyEnterMemberDTO studyEnterMemberDTO, Study study, Member member) {
        return StudyEnterMember.builder()
                .studyEnterMemberPk(studyEnterMemberDTO.getStudyEnterMemberPk())
                .study(study)
                .member(member)
                .content(studyEnterMemberDTO.getContent())
                .enterStatus(StudyEnterMemberStatus.PENDING) // 신청 시 초기 값은 '대기 중'
                .createDate(LocalDateTime.now().withSecond(0).withNano(0))
                .build();
    }
}
