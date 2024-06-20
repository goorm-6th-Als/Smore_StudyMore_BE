package com.als.SMore.domain.entity;

import com.als.SMore.study.notice.DTO.NoticeRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice_board")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_board_pk")
    private Long noticeBoardPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    @JsonIgnore
    private Study study;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    @Column(name= "time", nullable = false)
    private LocalDateTime time;

    public NoticeBoard(NoticeRequestDTO requestDTO, Study study){  //create
        this.study = study;
        this.noticeTitle = requestDTO.getNoticeTitle();
        this.noticeContent = requestDTO.getNoticeContent();
        this.time = LocalDateTime.now().withNano(0);
    }

    public void updateNotice(NoticeRequestDTO requestDTO){ //update
        this.noticeTitle = requestDTO.getNoticeTitle();
        this.noticeContent = requestDTO.getNoticeContent();
    }

}
