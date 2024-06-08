package com.als.SMore.study.studyCRUD.controller;

import com.als.SMore.domain.repository.StudyBoardRepository;
import com.als.SMore.study.studyCRUD.DTO.StudyBoardDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class StudyBoardController {

    private final StudyBoardRepository studyBoardRepository;

    /**
     * 모든 StudyBoard 엔티티를 조회하는 엔드포인트
     *
     * @return 모든 StudyBoard 엔티티의 DTO 리스트와 함께 OK 응답 반환
     */
    @GetMapping
    public ResponseEntity<List<StudyBoardDTO>> getAllStudyBoards() {
        // 모든 StudyBoard 엔티티를 조회하고 DTO로 변환하여 반환
        return ResponseEntity.ok(
                studyBoardRepository.findAll().stream()
                        .map(studyBoard -> new StudyBoardDTO(studyBoard, false))
                        .collect(Collectors.toList())
        );
    }

    /**
     * 특정 ID의 StudyBoard 엔티티를 조회하는 엔드포인트
     *
     * @param id 조회할 StudyBoard 엔티티의 ID
     * @return 조회된 StudyBoard 엔티티의 DTO와 함께 OK 응답 반환 상세정보인 content 포함)
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudyBoardDTO> getStudyBoardById(@PathVariable Long id) {
        // ID로 StudyBoard 엔티티 조회하고 DTO로 변환하여 반환
        return ResponseEntity.ok(
                studyBoardRepository.findById(id)
                        .map(studyBoard -> new StudyBoardDTO(studyBoard, true))
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 studyBoard ID: " + id))
        );
    }
}
