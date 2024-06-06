package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "study_board")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "modify_date")
    private Date modifyDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "close_date")
    private Date closeDate;
}
