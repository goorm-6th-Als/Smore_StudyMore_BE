package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "problem_type")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemType {

    @Id @Tsid
    @Column(name = "problem_type_pk")
    private Long problemTypePk;

    @ManyToOne
    @JoinColumn(name = "problem_pk", nullable = false)
    private Problem problem;

    @Column(name = "type_content")
    private String typeContent;

}
