package com.als.SMore.study.problem.controller;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.global.validator.GlobalValidator;
import com.als.SMore.study.problem.DTO.request.problem.ProblemCreateRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemGetAllRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemSummaryResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemUpdateResponseDTO;
import com.als.SMore.study.problem.service.ProblemService;
import com.als.SMore.study.problem.validator.ProblemValidator;
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study/{studyPk}/problem")
@RequiredArgsConstructor
@Slf4j
public class ProblemController {

    private final ProblemService problemService;
    private final ProblemValidator problemValidator;
    private final GlobalValidator globalValidator;
    private Long getMemberPk(){

        return MemberUtil.getUserPk();
    }

    // 문제 불러오기 (수정하기용)
    @GetMapping("/{problemPk}")
    public ProblemUpdateResponseDTO getProblem(@PathVariable("studyPk") Long studyPk, @PathVariable("problemPk") Long problemPk) {
        Problem problem = problemValidator.getProblem(problemPk);
        return problemService.getProblem(problem);
    }

    //
    @GetMapping
    public List<ProblemResponseDTO> getAllProblem(@PathVariable("studyPk") Long studyPk,
                                                  @RequestParam List<Long> studyProblemBankPk,
                                                  @RequestParam Integer max) {
        return problemService.getAllProblem(ProblemGetAllRequestDTO.builder().
                studyProblemBankPk(studyProblemBankPk).
                max(max).
                build());

    }
    //문제 요약List (방장이나 작성자가 수정할 때 사용할 용도)
    @GetMapping("/summary/{ProblemBankPk}")
    public List<ProblemSummaryResponseDTO> getAllSummaryProblem(@PathVariable String studyPk, @PathVariable("ProblemBankPk") Long problemBankPk) {
        StudyProblemBank studyProblemBank = problemValidator.getStudyProblemBank(problemBankPk);
        return problemService.getAllProblemSummary(studyProblemBank);
    }

    //지우는거
    @DeleteMapping("/{problemPk}")
    public void deleteProblem(@PathVariable("studyPk") Long studyPk, @PathVariable("problemPk") Long problemPk) {
        Problem problem = problemValidator.getProblem(problemPk);
        Member member = globalValidator.getMember(getMemberPk());
        problemService.deleteProblem(problem, member);
    }
    //수정하는거
    @PutMapping
    public void updateProblem(@PathVariable("studyPk") Long studyPk, @RequestBody ProblemUpdateRequestDTO problemUpdateRequestDTO) {
        Member member = globalValidator.getMember(getMemberPk());
        problemService.updateProblem(problemUpdateRequestDTO, member);
    }

    //만드는거
    @PostMapping
    public ResponseEntity<String> createProblem(@PathVariable("studyPk") Long studyPk, @RequestBody ProblemCreateRequestDTO problemCreateRequestDTO) {
        Member member = globalValidator.getMember(getMemberPk());
        problemService.createProblem(member, problemCreateRequestDTO);
        return ResponseEntity.ok(problemCreateRequestDTO.getStudyProblemBankPk().toString());
    }



}
