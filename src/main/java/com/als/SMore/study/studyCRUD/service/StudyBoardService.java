package com.als.SMore.study.studyCRUD.service;

import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.repository.StudyBoardRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.studyCRUD.DTO.StudyBoardDTO;
import com.als.SMore.study.studyCRUD.mapper.StudyBoardMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyBoardService {
    private static final Logger logger = LoggerFactory.getLogger(StudyService.class);
    private final StudyBoardRepository studyBoardRepository;
    private final StudyDetailRepository studyDetailRepository;

    /**
     * 모든 StudyBoard 조회
     * @return 모든 StudyBoardDTO 리스트
     */
    @Transactional(readOnly = true)
    public List<StudyBoardDTO> getAllStudyBoards() {
        List<StudyBoard> studyBoards = studyBoardRepository.findAll();

        return studyBoards.stream()
                .map(studyBoard -> {
                    StudyDetail studyDetail = studyDetailRepository.findByStudy_StudyPk(studyBoard.getStudy().getStudyPk())
                            .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_DETAIL));
                    return StudyBoardMapper.toDTO(studyBoard, studyDetail, false);
                })
                .collect(Collectors.toList());
    }

    /**
     * 스터디 게시물 상세 조회하기
     * @param studyBoardPk 조회할 StudyBoard의 PK
     * @return 조회된 StudyBoardDTO
     */
    @Transactional(readOnly = true)
    public StudyBoardDTO getStudyBoardByPk(Long studyBoardPk) {
        StudyBoard studyBoard = studyBoardRepository.findById(studyBoardPk)
                .orElseThrow(() -> {
                    logger.info("스터디 보드를 찾을 수 없습니다. ID: " + studyBoardPk);
                    return new CustomException(CustomErrorCode.NOT_FOUND_STUDY_BOARD);
                });
        StudyDetail studyDetail = studyDetailRepository.findByStudyStudyPk(studyBoard.getStudy().getStudyPk())
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_DETAIL));

        return StudyBoardMapper.toDTO(studyBoard, studyDetail,true);
    }

    /**
     * StudyBoard 페이징
     * @param pageable 페이지 정보
     * @return StudyBoardDTO의 페이지 객체
     */
    @Transactional(readOnly = true)
    public Page<StudyBoardDTO> getStudyBoardsPage(Pageable pageable) {
        return studyBoardRepository.findAll(pageable)
                .map(studyBoard -> {
                    StudyDetail studyDetail = studyDetailRepository.findByStudyStudyPk(studyBoard.getStudy().getStudyPk())
                            .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_DETAIL));
                    return StudyBoardMapper.toDTO(studyBoard, studyDetail,true);
                });
    }

    /**
     * 스터디 보드 검색 기능 수정날짜로 정렬
     * @param keyword 검색할 문자열
     * @param pageable 페이지 정보
     * @return 검색된 StudyBoardDTO의 페이지 객체
     */
    @Transactional(readOnly = true)
    public Page<StudyBoardDTO> searchStudyBoards(String keyword, Pageable pageable) {
        return studyBoardRepository.searchByAdTitle(keyword, pageable)
                .map(studyBoard -> {
                    StudyDetail studyDetail = studyDetailRepository.findByStudyStudyPk(studyBoard.getStudy().getStudyPk())
                            .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_DETAIL));
                    return StudyBoardMapper.toDTO(studyBoard, studyDetail,true);
                });
    }
}

