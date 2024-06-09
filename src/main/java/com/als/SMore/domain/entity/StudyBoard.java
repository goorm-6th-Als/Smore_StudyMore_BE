package com.als.SMore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study_board")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 순환 참조 방지.
@JsonIgnoreProperties({"study", "member"})
public class StudyBoard {

    @Id @Tsid
    @Column(name = "study_board_pk")
    private Long studyBoardPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Column(name = "ad_title", nullable = false)
    private String adTitle;

    @Column(name = "ad_content")
    private String adContent;

    @Column(name = "ad_summary")
    private String adSummary;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "modify_date")
    private LocalDate modifyDate;

    @Column(name = "close_date")
    private LocalDate closeDate;
}
