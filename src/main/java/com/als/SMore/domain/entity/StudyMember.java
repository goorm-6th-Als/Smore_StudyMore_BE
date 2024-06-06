package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyMember {

    @Id @Tsid
    @Column(name = "study_member_pk")
    private Long studyMemberPk;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;
}
