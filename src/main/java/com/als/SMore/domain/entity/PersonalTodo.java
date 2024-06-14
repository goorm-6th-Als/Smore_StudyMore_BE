package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "personal_todo")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTodo {

    @Id @Tsid
    @Column(name = "personal_todo_pk")
    private Long personalTodoPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_status", nullable = false)
    private TodoStatus scheduleStatus;

    @Column(name = "schedule_content")
    private String scheduleContent;

    @Column(name = "modify_date")
    private LocalDate modifyDate;

    @Column(name = "create_date", updatable = false)
    private LocalDate createDate;

}
