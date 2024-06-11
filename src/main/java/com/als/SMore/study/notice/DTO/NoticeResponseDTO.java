package com.als.SMore.study.notice.DTO;

import com.als.SMore.domain.entity.NoticeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeResponseDTO {
    private Long noticeBoardPk;
    private String noticeTitle;
    private String noticeContent;

    public NoticeResponseDTO(NoticeBoard noticeBoard){
        this.noticeBoardPk = noticeBoard.getNoticeBoardPk();
        this.noticeTitle = noticeBoard.getNoticeTitle();
        this.noticeContent = noticeBoard.getNoticeContent();
    }


}
