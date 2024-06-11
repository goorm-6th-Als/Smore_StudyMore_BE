package com.als.SMore.study.notice.service;

import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.NoticeBoardRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.notice.DTO.NoticeRequestDTO;
import com.als.SMore.study.notice.DTO.NoticeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final StudyRepository studyRepository;

    @Transactional(readOnly = true)
    public NoticeResponseDTO getNotice(Long studyPK, Long noticeBoardPK) {
        Study study = studyRepository.findById(studyPK)
                .orElseThrow(NoSuchElementException::new);
        List<NoticeBoard> NoticeBoards = noticeBoardRepository.findAllByStudy(study);

        Long nBoardPk = null;
        for (NoticeBoard noticeBoard : NoticeBoards) {
            Long pk = noticeBoard.getNoticeBoardPk();
            if (Objects.equals(pk, noticeBoardPK)) {
                nBoardPk = noticeBoardPK;
            }
        }

        if (nBoardPk == null) {
            throw new NoSuchElementException();
        } else {
            NoticeBoard board = noticeBoardRepository.findByNoticeBoardPk(nBoardPk);
            NoticeResponseDTO noticeResponseDTO = new NoticeResponseDTO();
            noticeResponseDTO.setNoticeBoardPk(noticeBoardPK);
            noticeResponseDTO.setNoticeTitle(board.getNoticeTitle());
            noticeResponseDTO.setNoticeContent(board.getNoticeContent());
            return noticeResponseDTO;
        }
    }

    public NoticeResponseDTO createNotice(Long studyPK, NoticeRequestDTO requestDTO) {
        //연관관계 주인 = noticeBoard (O) Study(X)

        //유저 정보 받아오면, 유저 정보 + 스터디 PK로 방장이 맞는지 확인하도록.
        Study study = studyRepository.findById(studyPK)
                .orElseThrow(NoSuchElementException ::new);
        NoticeBoard noticeBoard = noticeBoardRepository.saveAndFlush(new NoticeBoard(requestDTO,study));
        return new NoticeResponseDTO(noticeBoard);
    }



}
