package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "study_learning_time", indexes = {@Index(name = "member_index", columnList = "study_member_pk")})
public class StudyLearningTime {
    @Id
    @Tsid
    @Column(name = "study_learning_time_pk")
    private Long studyLearningTimePk;

    @ManyToOne
    @JoinColumn(name = "study_member_pk", nullable = false)
    private StudyMember studyMember;

    @Column(name = "learning_time")
    private Long learningTime;

    @Column(name = "learning_date")
    private LocalDate learningDate;

}
