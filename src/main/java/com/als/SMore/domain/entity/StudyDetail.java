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

    @Column(name = "max_people")
    private int maxPeople;

    @Column(name = "content")
    private String content;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "close_date")
    private LocalDate closeDate;
}
