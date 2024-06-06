package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notice_board")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoard {

    @Id @Tsid
    @Column(name = "notice_board_pk")
    private Long noticeBoardPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", nullable = false)
    private String noticeContent;
}
