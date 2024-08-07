package com.als.SMore.study.notice.service;

import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.NoticeBoardRepository;
import com.als.SMore.notification.dto.NotificationRequestDto;
import com.als.SMore.notification.service.NotificationService;
import com.als.SMore.study.notice.DTO.MessageResponseDTO;
import com.als.SMore.study.notice.DTO.NoticeRequestDTO;
import com.als.SMore.study.notice.DTO.NoticeResponseDTO;
import com.als.SMore.study.notice.validator.NoticeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeValidator noticeValidator;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public NoticeResponseDTO getNotice(Long studyPK, Long noticeBoardPK) {
        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        NoticeBoard noticeBoard = noticeValidator.findNoticeByBoardPkAndStudy(noticeBoardPK, study);
        return new NoticeResponseDTO(noticeBoard);
    }

    @Transactional(readOnly = true)
    public List<NoticeBoard> getAllNotice(Long studyPK) {
        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        return noticeBoardRepository.findAllByStudy(study);
    }

    public NoticeResponseDTO createNotice(Long studyPK, NoticeRequestDTO requestDTO, Long memberPk) {
        //연관관계 주인 = noticeBoard (O) Study(X)
        noticeValidator.CheckIsItManagerOfStudy(studyPK,memberPk);
        noticeValidator.validateTitleLength(requestDTO.getNoticeTitle());

        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        NoticeBoard noticeBoard = noticeBoardRepository.saveAndFlush(new NoticeBoard(requestDTO,study));
        notify(memberPk, studyPK, "공지 생성 성공");

        return new NoticeResponseDTO(noticeBoard);
    }

    public NoticeResponseDTO updateNotice(Long studyPK, Long noticeBoardPK, NoticeRequestDTO requestDTO, Long memberPk) {
         noticeValidator.CheckIsItManagerOfStudy(studyPK,memberPk);
        noticeValidator.validateTitleLength(requestDTO.getNoticeTitle());
        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        NoticeBoard noticeBoard = noticeValidator.findNoticeByBoardPkAndStudy(noticeBoardPK, study);
        noticeBoard.updateNotice(requestDTO);
        notify(memberPk, studyPK, "공지 수정 성공"); //테스트 용으로 남겨둠. 추후 지울 예정

        return new NoticeResponseDTO(noticeBoard, requestDTO);
    }

    public MessageResponseDTO deleteNotice(Long studyPK, Long noticeBoardPK, Long memberPk) {
        noticeValidator.CheckIsItManagerOfStudy(studyPK,memberPk);

        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        NoticeBoard noticeBoard = noticeValidator.findNoticeByBoardPkAndStudy(noticeBoardPK, study);

        noticeBoardRepository.delete(noticeBoard);
        return new MessageResponseDTO("삭제 완료");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notify(Long receiverPk, Long studyPk, String content) {
        NotificationRequestDto notificationRequest = NotificationRequestDto.builder()
                .receiverPk(receiverPk)
                .studyPk(studyPk)
                .content(content)
                .build();
        notificationService.send(notificationRequest);
    }


}
