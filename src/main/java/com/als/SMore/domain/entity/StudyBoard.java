package com.als.SMore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study_board")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
// 순환 참조 방지.
@JsonIgnoreProperties({"study", "member"})
public class StudyBoard {

    @Id @Tsid
    @Column(name = "study_board_pk")
    private Long studyBoardPk;

    @OneToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "ad_title", nullable = false)
    private String adTitle;

    @Column(name = "ad_content", nullable = false)
    private String adContent;

    @Column(name = "ad_summary", nullable = false)
    private String adSummary;

    @Column(name = "modify_date", nullable = false)
    private LocalDate modifyDate;

    @Column(name = "image_uri")
    private String imageUri;

}
