package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_detail")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyDetail {

    @Id @Tsid
    @Column(name = "study_pk")
    private Long studyPk;

    @Column(name = "image_uri")
    private String imageUri;

    @Column(name = "max_people", nullable = false)
    private Integer maxPeople;

    @Column(name = "overview")
    private String overview;

    @Column(name = "open_time", nullable = false)
    private String openTime = "00:00:00";

    @Column(name = "close_time", nullable = false)
    private String closeTime = "23:59:59";
}
