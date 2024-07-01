package com.als.SMore.study.notice.DTO;

import com.als.SMore.domain.entity.NoticeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoticeResponseDTO {
    private String noticeBoardPk;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime time;

    public NoticeResponseDTO(NoticeBoard noticeBoard){
        this.noticeBoardPk = String.valueOf(noticeBoard.getNoticeBoardPk());
        this.noticeTitle = noticeBoard.getNoticeTitle();
        this.noticeContent = noticeBoard.getNoticeContent();
        this.time = noticeBoard.getTime();
    }

    public NoticeResponseDTO(NoticeBoard noticeBoard, NoticeRequestDTO requestDTO){
        this.noticeBoardPk = String.valueOf(noticeBoard.getNoticeBoardPk());
        this.noticeTitle = requestDTO.getNoticeTitle();
        this.noticeContent = requestDTO.getNoticeContent();
        this.time =noticeBoard.getTime();
    }


}
