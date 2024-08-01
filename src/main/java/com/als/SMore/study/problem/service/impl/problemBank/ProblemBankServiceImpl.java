package com.als.SMore.study.problem.service.impl.problemBank;


import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.ProblemOptionsRepository;
import com.als.SMore.domain.repository.ProblemRepository;
import com.als.SMore.domain.repository.StudyProblemBankRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.global.validator.GlobalValidator;
import com.als.SMore.study.attendance.validator.AttendanceValidator;
import com.als.SMore.study.problem.DTO.request.problemBank.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemOptionResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.PersonalProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankSummaryResponseDTO;
import com.als.SMore.study.problem.service.ProblemBankService;
import com.als.SMore.study.problem.validator.ProblemBankValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProblemBankServiceImpl implements ProblemBankService {

    private final ProblemRepository problemRepository;
    private final StudyProblemBankRepository studyProblemBankRepository;
    private final ProblemOptionsRepository problemOptionsRepository;
    private final ProblemBankValidator problemBankValidator;


    //=========================================== problemBank CRUD =====================================

    @Override
    public Long createProblemBank(Member member, Study study, String bankName) {
        //제목 글자 수 확인(1 - 30)
        //문제은행 수 확인 (30이하)

        return studyProblemBankRepository.save(StudyProblemBank.of(member, study, bankName))
                .getStudyProblemBankPk();
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProblemBankResponseDTO> getAllProblemBank(Member member, Study study) {
        //studyPk로 가져온 study_problem_bank 에서 접근한 유저가 작성자나 방장인지 확인 후 삭제권한 허가

        List<StudyProblemBank> studyProblemBanks = studyProblemBankRepository.findByStudyOrderByStudyProblemBankPkDesc(study);
        List<ProblemBankResponseDTO> problemBankResponseDTOList = new ArrayList<>();

        for (StudyProblemBank studyProblemBank : studyProblemBanks) {
            problemBankResponseDTOList.add(ProblemBankResponseDTO.
                    of(studyProblemBank, problemBankValidator.isManager(member.getMemberPk(), studyProblemBank)));

        }
        return problemBankResponseDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemBankResponseDTO getProblemBank(StudyProblemBank problemBank, Member member) {
        //problemBankPk 유효성 검사, 작성자 or 방장인 검사
        ProblemBankResponseDTO problemBankResponseDTO = ProblemBankResponseDTO.
                of(problemBank, problemBankValidator.isManager(member.getMemberPk(), problemBank));

        List<ProblemResponseDTO> problemResponseDTOList = new ArrayList<>();
        List<Problem> problemList = problemRepository.findByStudyProblemBank(problemBank);
        for (Problem problem : problemList) {
            List<ProblemOptions> problemOptionList = problemOptionsRepository.findAllByProblemOrderByOptionsNum(problem);
            List<ProblemOptionResponseDTO> problemOptionResponseDTOList = new ArrayList<>();
            for (ProblemOptions options : problemOptionList) {
                problemOptionResponseDTOList.add(ProblemOptionResponseDTO.of(options));
            }
            problemResponseDTOList.add(ProblemResponseDTO.of(problem, problemOptionResponseDTOList));
        }
        problemBankResponseDTO = problemBankResponseDTO.toBuilder().problemList(problemResponseDTOList).build();
        return problemBankResponseDTO;
    }

    @Override
    public void deleteProblemBank(Member member, StudyProblemBank problemBank) {
        if (problemBankValidator.isManager(member.getMemberPk(), problemBank)) {
            studyProblemBankRepository.deleteById(problemBank.getStudyProblemBankPk());
        } else throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
    }

    @Override
    @Transactional
    public ProblemBankResponseDTO updateProblemBank(Member member, ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO) {
        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankUpdateRequestDTO.getProblemBankPk());
        if (problemBankValidator.isManager(member.getMemberPk(), problemBank)) {
            problemBank = studyProblemBankRepository.save(problemBank.toBuilder().
                    bankName(problemBankUpdateRequestDTO.getProblemBankName()).
                    build());
        } else throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
        return ProblemBankResponseDTO.of(problemBank, problemBankValidator.isManager(member.getMemberPk(), problemBank));
    }


    @Override
    public List<PersonalProblemBankResponseDTO> getPersonalProblemBank(Member member, Study study) {
        List<StudyProblemBank> problemBankList = studyProblemBankRepository.findByMemberAndStudy(member, study);
        List<PersonalProblemBankResponseDTO> personalProblemBankResponseDTOList = new ArrayList<>();

        for (StudyProblemBank studyProblemBank : problemBankList) {
            personalProblemBankResponseDTOList.add(
                    PersonalProblemBankResponseDTO.of(studyProblemBank, problemRepository.countByStudyProblemBank(studyProblemBank))
            );
        }

        return personalProblemBankResponseDTOList;
    }

    @Override
    public List<ProblemBankSummaryResponseDTO> getAllProblemBankSummary(Study study) {

        List<StudyProblemBank> problemBankList = studyProblemBankRepository.findByStudyOrderByStudyProblemBankPkDesc(study);
        List<ProblemBankSummaryResponseDTO> problemBankSummaryResponseDTOList = new ArrayList<>();

        for (StudyProblemBank studyProblemBank : problemBankList) {
            problemBankSummaryResponseDTOList.add(ProblemBankSummaryResponseDTO.of(
                    studyProblemBank, problemRepository.countByStudyProblemBank(studyProblemBank))
            );
        }
        return problemBankSummaryResponseDTOList;
    }


}
