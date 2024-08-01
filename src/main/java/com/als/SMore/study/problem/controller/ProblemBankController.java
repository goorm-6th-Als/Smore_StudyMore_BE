package com.als.SMore.study.problem.controller;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.global.validator.GlobalValidator;
import com.als.SMore.study.problem.DTO.request.problemBank.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.PersonalProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankSummaryResponseDTO;
import com.als.SMore.study.problem.service.ProblemBankService;
import com.als.SMore.study.problem.validator.ProblemBankValidator;
import com.als.SMore.study.problem.validator.ProblemValidator;
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("study/{studyPk}/problem")
@RequiredArgsConstructor
@Slf4j
public class ProblemBankController {
    private final ProblemBankService problemBankService;
    private final GlobalValidator globalValidator;
    private final ProblemBankValidator problemBankValidator;

    /**
     * 현재 로그인된 사용자의 Pk를 가져오는 메서드
     * @return 로그인된 사용자의 Pk
     */
    private Long getMemberPk() {
        return MemberUtil.getUserPk();
    }

    /**
     * 스터디의 모든 문제은행을 조회
     * @param studyPk 스터디의 Pk
     * @return 문제은행 목록
     */
    @GetMapping("/bank")
    public List<ProblemBankSummaryResponseDTO> getAllStudyProblemBank(@PathVariable Long studyPk) {
        Study study = globalValidator.getStudy(studyPk);
        return problemBankService.getAllProblemBankSummary(study);
    }

    /**
     * 스터디에서 본인이 만든 문제 은행들을 조회
     * @param studyPk
     * @return
     */

    @GetMapping("/bank/personal")
    public List<PersonalProblemBankResponseDTO> getAllPersonalStudyProblemBank(@PathVariable Long studyPk) {
        Member member = globalValidator.getMember(getMemberPk());
        Study study = globalValidator.getStudy(studyPk);

        return problemBankService.getPersonalProblemBank(member, study);
    }


    /**
     * 문제은행을 조회
     * @param studyPk 스터디의 Pk
     * @param problemBankPk 문제은행의 Pk
     * @return 문제은행 응답 DTO
     */
    @GetMapping("/bank/{problemBankPk}")
    public ProblemBankResponseDTO getStudyProblemBank(@PathVariable Long studyPk, @PathVariable Long problemBankPk) {
        Member member = globalValidator.getMember(getMemberPk());
        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankPk);
        return problemBankService.getProblemBank(problemBank, member);
    }

    /**
     * 새로운 문제은행을 생성
     * @param studyPk 스터디의 Pk
     * @param problemBankMap 문제은행 정보 (문제은행 이름)
     */
    @PostMapping("/bank")
    public Map<String, String> createProblemBank(@PathVariable Long studyPk, @RequestBody Map<String, String> problemBankMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Member member = globalValidator.getMember(getMemberPk());
        Study study = globalValidator.getStudy(studyPk);
        problemBankValidator.bankNameLength(problemBankMap.get("problemBankName"));
        problemBankValidator.studyProblemBankSize(study);
        resultMap.put("problemBankPk", problemBankService.createProblemBank(member, study, problemBankMap.get("problemName")).toString());
        return resultMap;
    }

    /**
     * 문제은행을 삭제
     * @param studyPk 스터디의 Pk
     * @param problemBankPk 문제은행의 Pk
     */
    @DeleteMapping("/bank/{problemBankPk}")
    public void deleteProblemBank(@PathVariable Long studyPk, @PathVariable Long problemBankPk) {
        Member member = globalValidator.getMember(getMemberPk());
        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankPk);
        problemBankService.deleteProblemBank(member, problemBank);
    }

    /**
     * 문제은행을 업데이트
     * @param studyPk 스터디의 Pk
     * @param problemBankUpdateRequestDTO 문제은행 업데이트 요청 DTO
     * @return 업데이트된 문제은행 응답 DTO
     */
    @PutMapping("/bank")
    public ProblemBankResponseDTO updateProblemBank(@PathVariable Long studyPk, @RequestBody ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO) {
        Member member = globalValidator.getMember(getMemberPk());
        return problemBankService.updateProblemBank(member, problemBankUpdateRequestDTO);
    }
}
