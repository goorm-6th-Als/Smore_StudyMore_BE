package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_schedule_place_date")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudySchedulePlaceDate {

    @Id @Tsid
    @Column(name = "study_schedule_place_date_pk")
    private Long studySchedulePlaceDatePk;

    @ManyToOne
    @JoinColumn(name = "study_schedule_pk", nullable = false)
    private StudySchedule studySchedule;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
