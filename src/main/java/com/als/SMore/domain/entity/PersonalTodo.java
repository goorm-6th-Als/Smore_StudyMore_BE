package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "personal_todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_todo_pk")
    private Long personalTodoPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @Column(name = "schedule_status", nullable = false)
    private String scheduleStatus;

    @Column(name = "schedule_content")
    private String scheduleContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_date", nullable = false)
    private Date scheduleDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;
}
