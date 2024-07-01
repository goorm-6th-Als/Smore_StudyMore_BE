package com.als.SMore.study.enter.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyEnterMember;
import com.als.SMore.domain.entity.StudyEnterMemberStatus;
import com.als.SMore.study.enter.DTO.StudyEnterMemberDTO;
import java.time.LocalDateTime;

public class StudyEnterMemberMapper {

    public static StudyEnterMemberDTO toDTO(StudyEnterMember studyEnterMember) {
        return StudyEnterMemberDTO.builder()
                .studyEnterMemberPk(studyEnterMember.getStudyEnterMemberPk())
                .studyPk(studyEnterMember.getStudy().getStudyPk())
                .memberPk(studyEnterMember.getMember().getMemberPk())
                .content(studyEnterMember.getContent())
                .enterStatus(studyEnterMember.getEnterStatus())
                .createDate(studyEnterMember.getCreateDate())
                .build();
    }

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

    public static StudyEnterMember updateEntity(StudyEnterMemberDTO studyEnterMemberDTO, StudyEnterMember studyEnterMember) {
        return studyEnterMember.toBuilder()
                .content(studyEnterMemberDTO.getContent() != null ? studyEnterMemberDTO.getContent() : studyEnterMember.getContent())
                .createDate(LocalDateTime.now().withSecond(0).withNano(0))
                .build();
    }
}
