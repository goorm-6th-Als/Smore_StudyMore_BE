package com.als.SMore.study.problem.controller;

import com.als.SMore.study.problem.DTO.request.problemBank.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankResponseDTO;
import com.als.SMore.study.problem.service.ProblemBankService;
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("study/{studyPk}/problem")
@RequiredArgsConstructor
@Slf4j
public class ProblemBankController{
    private final ProblemBankService problemBankService;
    private Long getMemberPk(){

        return MemberUtil.getUserPk();
    }
    //=======================================ProblemBank=============================
    @GetMapping("/bank")
    public List<ProblemBankResponseDTO> getAllStudyProblemBank(@PathVariable Long studyPk){

        return problemBankService.getAllProblemBank(getMemberPk(), studyPk);
    }
    @GetMapping("/bank/{problemBankPk}")
    public ProblemBankResponseDTO getStudyProblemBank(@PathVariable Long studyPk, @PathVariable Long problemBankPk){

        return problemBankService.getProblemBank(problemBankPk, getMemberPk());
    }

    @PostMapping("/bank")
    public void createProblemBank(@PathVariable Long studyPk, @RequestBody Map<String, String> problemBankMap){
        problemBankService.createProblemBank(getMemberPk(), studyPk, problemBankMap.get("problemName"));
    }

    @DeleteMapping("/bank/{problemBankPk}")
    public void deleteProblemBank(@PathVariable Long studyPk, @PathVariable Long problemBankPk){
        problemBankService.deleteProblemBank(getMemberPk(), problemBankPk);
    }

    @PutMapping("/bank")
    public ProblemBankResponseDTO updateProblemBank(@PathVariable Long studyPk, @RequestBody ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO){
        log.info("problemBankUpdateRequestDTO.getProblemBankName() = {}", problemBankUpdateRequestDTO.getProblemBankName());
        log.info("problemBankUpdateRequestDTO.getProblemBankPk() = {}", problemBankUpdateRequestDTO.getProblemBankPk());
        return problemBankService.updateProblemBank(getMemberPk(),problemBankUpdateRequestDTO);
    }

}
