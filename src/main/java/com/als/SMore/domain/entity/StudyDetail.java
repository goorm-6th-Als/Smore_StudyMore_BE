package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "study_detail")
@Getter
@Builder(toBuilder = true)
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
    private int maxPeople;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "close_date", nullable = false)
    private LocalDate closeDate;

}
