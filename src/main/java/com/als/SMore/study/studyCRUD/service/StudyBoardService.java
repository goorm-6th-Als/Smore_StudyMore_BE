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
     * 모든 StudyBoard 엔티티를 조회하여 DTO 리스트로 반환합니다.
     *
     * @return 모든 StudyBoardDTO 리스트
     */
    public List<StudyBoardDTO> getAllStudyBoards() {
        return studyBoardRepository.findAll().stream()
                .map(studyBoard -> new StudyBoardDTO(studyBoard, false))
                .collect(Collectors.toList());
    }

    /**
     * ID로 StudyBoard 엔티티를 조회하여 DTO로 반환합니다.
     *
     * @param id 조회할 StudyBoard의 ID
     * @return 조회된 StudyBoardDTO
     */
    public StudyBoardDTO getStudyBoardById(Long id) {
        StudyBoard studyBoard = studyBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 studyBoard ID: " + id));
        return new StudyBoardDTO(studyBoard, true);
    }

    /**
     * Pageable 객체를 사용하여 StudyBoard 엔티티의 페이지를 조회하고, DTO 페이지로 반환합니다.
     *
     * @param pageable 페이지 정보
     * @return StudyBoardDTO의 페이지 객체
     */
    public Page<StudyBoardDTO> getStudyBoardsPage(Pageable pageable) {
        return studyBoardRepository.findAll(pageable)
                .map(studyBoard -> new StudyBoardDTO(studyBoard, false));
    }
}