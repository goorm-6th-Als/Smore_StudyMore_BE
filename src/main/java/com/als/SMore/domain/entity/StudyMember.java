package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study_member")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Tsid
    @Column(name = "study_member_pk")
    private Long studyMemberPk;

    // 공부시간 Cascade 삭제조건 추가.
    @OneToMany(mappedBy = "studyMember", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudyLearningTime> studyLearningTimes;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "enter_date", nullable = false)
    private LocalDate enterDate;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;
}
