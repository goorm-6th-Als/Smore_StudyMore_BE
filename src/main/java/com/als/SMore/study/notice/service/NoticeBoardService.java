package com.als.SMore.study.notice.service;

import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.NoticeBoardRepository;
import com.als.SMore.study.notice.DTO.MessageResponseDTO;
import com.als.SMore.study.notice.DTO.NoticeRequestDTO;
import com.als.SMore.study.notice.DTO.NoticeResponseDTO;
import com.als.SMore.study.notice.validator.NoticeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeValidator noticeValidator;

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

    public NoticeResponseDTO createNotice(Long studyPK, NoticeRequestDTO requestDTO, Long requestorPk) {
        //연관관계 주인 = noticeBoard (O) Study(X)
        //유저 정보 받아오면, 유저 정보 + 스터디 PK로 방장이 맞는지 확인하도록.

        noticeValidator.CheckIsItManagerOfStudy(studyPK,requestorPk);
        noticeValidator.validateTitleLength(requestDTO.getNoticeTitle());

        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        NoticeBoard noticeBoard = noticeBoardRepository.saveAndFlush(new NoticeBoard(requestDTO,study));
        return new NoticeResponseDTO(noticeBoard);
    }

    public NoticeResponseDTO updateNotice(Long studyPK, Long noticeBoardPK, NoticeRequestDTO requestDTO, Long requestorPk) {
        //유저 정보 받아오면, 유저 정보 + 스터디 PK로 방장이 맞는지 확인하도록.
        noticeValidator.CheckIsItManagerOfStudy(studyPK,requestorPk);

        noticeValidator.validateTitleLength(requestDTO.getNoticeTitle());

        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        NoticeBoard noticeBoard = noticeValidator.findNoticeByBoardPkAndStudy(noticeBoardPK, study);
        noticeBoard.updateNotice(requestDTO);

        return new NoticeResponseDTO(noticeBoardPK, requestDTO);
    }

    public MessageResponseDTO deleteNotice(Long studyPK, Long noticeBoardPK, Long requestorPk) {
        noticeValidator.CheckIsItManagerOfStudy(studyPK,requestorPk);

        Study study = noticeValidator.findStudyByStudyPk(studyPK);
        NoticeBoard noticeBoard = noticeValidator.findNoticeByBoardPkAndStudy(noticeBoardPK, study);

        noticeBoardRepository.delete(noticeBoard);
        return new MessageResponseDTO("삭제 완료");
    }

}
