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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Study {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_pk")
    private Long studyPk;

    // 스터디 디테일, 스터디 멤버, 스터디 보드 종속
    @OneToOne(mappedBy = "study", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private StudyDetail studyDetail;

    @OneToMany(mappedBy = "study", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudyMember> studyMembers;

    @OneToOne(mappedBy = "study", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private StudyBoard studyBoards;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Column(name = "study_name", nullable = false)
    private String studyName;

}