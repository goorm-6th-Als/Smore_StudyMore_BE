package com.als.SMore.study.problem.controller;

import com.als.SMore.study.problem.DTO.request.problem.ProblemCreateRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemGetAllRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemSummaryResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemUpdateResponseDTO;
import com.als.SMore.study.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study/{studyPk}/problem")
@RequiredArgsConstructor
@Slf4j
public class ProblemController {

    private final ProblemService problemService;

    private Long getMemberPk(){

        return 588964788038193070L;
    }

    // 문제 불러오기 (수정하기용)
    @GetMapping("/{problemPk}")
    public ProblemUpdateResponseDTO getProblem(@PathVariable("studyPk") Long studyPk, @PathVariable("problemPk") Long problemPk) {
        return problemService.getProblem(problemPk);
    }

    //
    @GetMapping
    public List<ProblemResponseDTO> getAllProblem(@PathVariable("studyPk") Long studyPk, @RequestBody ProblemGetAllRequestDTO problemGetAllRequestDTO) {
        return problemService.getAllProblem(problemGetAllRequestDTO);

    }
    //문제 요약List (방장이나 작성자가 수정할 때 사용할 용도)
    @GetMapping("/summary/{ProblemBankPk}")
    public List<ProblemSummaryResponseDTO> getAllSummaryProblem(@PathVariable String studyPk, @PathVariable("ProblemBankPk") Long problemBankPk) {
        return problemService.getAllProblemSummary(problemBankPk);
    }

    //지우는거
    @DeleteMapping("/{problemPk}")
    public void deleteProblem(@PathVariable("studyPk") Long studyPk, @PathVariable("problemPk") Long problemPk) {
        problemService.deleteProblem(problemPk, getMemberPk());
    }
    //수정하는거
    @PutMapping
    public void updateProblem(@PathVariable("studyPk") Long studyPk, @RequestBody ProblemUpdateRequestDTO problemUpdateRequestDTO) {
        problemService.updateProblem(problemUpdateRequestDTO, getMemberPk());
    }

    //만드는거
    @PostMapping
    public void createProblem(@PathVariable("studyPk") Long studyPk, @RequestBody ProblemCreateRequestDTO problemCreateRequestDTO) {
        problemService.createProblem(getMemberPk(), problemCreateRequestDTO);

    }



}
