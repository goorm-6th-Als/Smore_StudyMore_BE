package com.als.SMore.study.problem.service.impl.problemBank;


import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.ProblemOptionsRepository;
import com.als.SMore.domain.repository.ProblemRepository;
import com.als.SMore.domain.repository.StudyProblemBankRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
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

    private final AttendanceValidator attendanceValidator; // member랑 study 가져올 때만 쓴다
    private final ProblemBankValidator problemBankValidator;


    //=========================================== problemBank CRUD =====================================

    @Override
    public Long createProblemBank(Long memberPk, Long studyPk, String bankName) {
        //제목 글자 수 확인(1 - 30)
        //문제은행 수 확인 (30이하)

        problemBankValidator.bankNameLength(bankName);

        Study study = attendanceValidator.getStudy(studyPk);
        problemBankValidator.studyProblemBankSize(study);

        Member member = attendanceValidator.getMember(memberPk);

        return studyProblemBankRepository.save(StudyProblemBank.of(member, study, bankName))
                .getStudyProblemBankPk();
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProblemBankResponseDTO> getAllProblemBank(Long memberPk, Long studyPk) {
        //studyPk로 가져온 study_problem_bank 에서 접근한 유저가 작성자나 방장인지 확인 후 삭제권한 허가

        Study study = attendanceValidator.getStudy(studyPk);
        List<StudyProblemBank> studyProblemBanks = studyProblemBankRepository.findByStudyOrderByStudyProblemBankPkDesc(study);
        List<ProblemBankResponseDTO> problemBankResponseDTOList = new ArrayList<>();

        for (StudyProblemBank studyProblemBank : studyProblemBanks) {

            problemBankResponseDTOList.add(ProblemBankResponseDTO.
                    of(studyProblemBank, problemBankValidator.isManager(memberPk, studyProblemBank)));

        }
        return problemBankResponseDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemBankResponseDTO getProblemBank(Long problemBankPk, Long memberPk) {
        //problemBankPk 유효성 검사, 작성자 or 방장인 검사

        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankPk);

        ProblemBankResponseDTO problemBankResponseDTO = ProblemBankResponseDTO.
                of(problemBank, problemBankValidator.isManager(memberPk, problemBank));

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
    public void deleteProblemBank(Long memberPk, Long problemBankPk) {
        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankPk);
        if (problemBankValidator.isManager(memberPk, problemBank)) {
            studyProblemBankRepository.deleteById(problemBankPk);
        } else throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
    }

    @Override
    @Transactional
    public ProblemBankResponseDTO updateProblemBank(Long memberPk, ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO) {
        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankUpdateRequestDTO.getProblemBankPk());
        if (problemBankValidator.isManager(memberPk, problemBank)) {
            problemBank = studyProblemBankRepository.save(problemBank.toBuilder().
                    bankName(problemBankUpdateRequestDTO.getProblemBankName()).
                    build());
        } else throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
        return ProblemBankResponseDTO.of(problemBank, problemBankValidator.isManager(memberPk, problemBank));
    }


    @Override
    public List<PersonalProblemBankResponseDTO> getPersonalProblemBank(Long memberPk, Long studyPk) {
        Member member = problemBankValidator.getMember(memberPk);
        Study study = problemBankValidator.getStudy(studyPk);
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
    public List<ProblemBankSummaryResponseDTO> getAllProblemBankSummary(Long studyPk) {

        Study study = problemBankValidator.getStudy(studyPk);
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
