package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "study_enter_member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyEnterMember {
    @Id
    @Tsid
    @Column(name = "study_enter_member_pk", nullable = false)
    private Long studyEnterMemberPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "enter_status", nullable = false)
    private StudyEnterMemberStatus enterStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

}

