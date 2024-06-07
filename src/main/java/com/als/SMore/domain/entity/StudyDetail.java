package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "study_detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyDetail {

    @Id
    @Column(name = "study_pk")
    private Long studyPk;

    @OneToOne
    @MapsId
    @JoinColumn(name = "study_pk")
    private Study study;

    @Column(name = "image_uri")
    private String imageUri;

    @Column(name = "max_people", nullable = false)
    private Integer maxPeople;

    @Column(name = "overview")
    private String overview;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;

    @Column(name = "close_date", nullable = false)
    private LocalDate closeDate;
}
