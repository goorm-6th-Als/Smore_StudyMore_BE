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
    @JoinColumn(name = "member_pk2", nullable = false)
    private Member member;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "entrer_status")
    private String entrerStatus;

    @Column(name = "creat_date")
    private LocalDateTime creatDate;

}

