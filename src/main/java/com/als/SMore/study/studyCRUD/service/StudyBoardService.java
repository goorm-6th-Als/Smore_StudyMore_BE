package com.als.SMore.study.studyCRUD.service;

import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.repository.StudyBoardRepository;
import com.als.SMore.study.studyCRUD.DTO.StudyBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyBoardService {

    private final StudyBoardRepository studyBoardRepository;

    /**
     * 모든 StudyBoard 조회
     * @return 모든 StudyBoardDTO 리스트
     */
    public List<StudyBoardDTO> getAllStudyBoards() {
        return studyBoardRepository.findAll().stream()
                .map(studyBoard -> StudyBoardDTO.fromEntity(studyBoard, false))
                .collect(Collectors.toList());
    }

    /**
     * 스터디 게시물 상세 조회하기.
     * @param id 조회할 StudyBoard의 ID
     * @return 조회된 StudyBoardDTO
     */
    public StudyBoardDTO getStudyBoardById(Long id) {
        StudyBoard studyBoard = studyBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 studyBoard ID: " + id));
        return StudyBoardDTO.fromEntity(studyBoard, true);
    }

    /**
     * StudyBoard  페이징
     * @param pageable 페이지 정보
     * @return StudyBoardDTO의 페이지 객체
     */
    public Page<StudyBoardDTO> getStudyBoardsPage(Pageable pageable) {
        return studyBoardRepository.findAll(pageable)
                .map(studyBoard -> StudyBoardDTO.fromEntity(studyBoard, false));
    }
//
//    /**
//     * 스터디 보드 검색 기능 수정날짜로 정렬
//     * @param keyword 검색할 문자열
//     * @param pageable 페이지 정보
//     * @return 검색된 StudyBoardDTO의 페이지 객체
//     */
    public Page<StudyBoardDTO> searchStudyBoards(String keyword, Pageable pageable) {
        return studyBoardRepository.searchByAdTitle(keyword, pageable)
                .map(studyBoard -> StudyBoardDTO.fromEntity(studyBoard, false));
    }
}