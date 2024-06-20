package com.als.SMore.study.problem.controller;

import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.study.problem.DTO.request.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.ProblemBankResponseDTO;
import com.als.SMore.study.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("study/{studyPk}/problem")
@RequiredArgsConstructor
public class ProblemController {
    private static final Logger log = LoggerFactory.getLogger(ProblemController.class);
    private final ProblemService problemService;
    private Long getMemberPk(){

        return 588964788038193070L;
    }
    //=======================================ProblemBank=============================
    @GetMapping("/bank")
    public List<ProblemBankResponseDTO> getAllStudyProblemBank(@PathVariable Long studyPk){

        return problemService.getAllProblemBank(getMemberPk(), studyPk);
    }
    @GetMapping("/bank/{problemBankPk}")
    public ProblemBankResponseDTO getStudyProblemBank(@PathVariable Long studyPk, @PathVariable Long problemBankPk){

        return problemService.getProblemBank(problemBankPk, getMemberPk());
    }

    @PostMapping("/bank")
    public void createProblemBank(@PathVariable Long studyPk, @RequestBody Map<String, String> problemBankMap){
        problemService.createProblemBank(getMemberPk(), studyPk, problemBankMap.get("problemName"));
    }

    @DeleteMapping("/bank/{problemBankPk}")
    public void deleteProblemBank(@PathVariable Long studyPk, @PathVariable Long problemBankPk){
        problemService.deleteProblemBank(getMemberPk(), problemBankPk);
    }

    @PutMapping("/bank")
    public ProblemBankResponseDTO updateProblemBank(@PathVariable Long studyPk, @RequestBody ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO){
        log.info("problemBankUpdateRequestDTO.getProblemBankName() = {}", problemBankUpdateRequestDTO.getProblemBankName());
        log.info("problemBankUpdateRequestDTO.getProblemBankPk() = {}", problemBankUpdateRequestDTO.getProblemBankPk());
        return problemService.updateProblemBank(getMemberPk(),problemBankUpdateRequestDTO);
    }

}
