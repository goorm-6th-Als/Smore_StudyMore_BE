package com.als.SMore.study.problem.service.impl.problemBank;


import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.domain.repository.ProblemRepository;
import com.als.SMore.domain.repository.StudyProblemBankRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.attendance.validator.AttendanceValidator;
import com.als.SMore.study.problem.DTO.request.problemBank.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankResponseDTO;
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

    private final AttendanceValidator attendanceValidator; // member랑 study 가져올 때만 쓴다
    private final ProblemBankValidator problemBankValidator;


    //=========================================== problemBank CRUD =====================================

    @Override
    public void createProblemBank(Long memberPk, Long studyPk, String bankName) {
        //제목 글자 수 확인(1 - 30)
        //문제은행 수 확인 (30이하)

        problemBankValidator.bankNameLength(bankName);

        Study study = attendanceValidator.getStudy(studyPk);
        problemBankValidator.studyProblemBankSize(study);

        Member member = attendanceValidator.getMember(memberPk);

        studyProblemBankRepository.save(StudyProblemBank.builder()
                .bankName(bankName)
                .member(member)
                .study(study)
                .build()
        );
    }



    @Override
    @Transactional(readOnly = true)
    public List<ProblemBankResponseDTO> getAllProblemBank(Long memberPk, Long studyPk) {
        //studyPk로 가져온 study_problem_bank 에서 접근한 유저가 작성자나 방장인지 확인 후 삭제권한 허가

        Study study = attendanceValidator.getStudy(studyPk);
        List<StudyProblemBank> studyProblemBanks = studyProblemBankRepository.findByStudyOrderByStudyProblemBankPkDesc(study);
        List<ProblemBankResponseDTO> problemBankResponseDTOList = new ArrayList<>();

        for (StudyProblemBank studyProblemBank : studyProblemBanks) {

            problemBankResponseDTOList.add(new ProblemBankResponseDTO(studyProblemBank.getStudyProblemBankPk() //pk
                    , studyProblemBank.getBankName()  //문제은행 이름
                    , studyProblemBank.getMember().getNickName() // 작성자
                    , problemBankValidator.isManager(memberPk, studyProblemBank))); // 권한

        }
        return problemBankResponseDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemBankResponseDTO getProblemBank(Long problemBankPk, Long memberPk) {
        //problemBankPk 유효성 검사, 작성자 or 방장인 검사

        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankPk);
        return new ProblemBankResponseDTO(problemBank.getStudyProblemBankPk(),
                problemBank.getBankName(),
                problemBank.getMember().getNickName(),
                problemBankValidator.isManager(memberPk, problemBank));
    }

    @Override
    public void deleteProblemBank(Long memberPk, Long problemBankPk) {
        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankPk);
        if(problemBankValidator.isManager(memberPk, problemBank)){
            studyProblemBankRepository.deleteById(problemBankPk);
        }else throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
    }

    @Override
    @Transactional
    public ProblemBankResponseDTO updateProblemBank(Long memberPk, ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO) {
        StudyProblemBank problemBank = problemBankValidator.getProblemBank(problemBankUpdateRequestDTO.getProblemBankPk());
        if(problemBankValidator.isManager(memberPk, problemBank)){
            problemBank = studyProblemBankRepository.save(problemBank.toBuilder().
                    bankName(problemBankUpdateRequestDTO.getProblemBankName()).
                    build());
        }else throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
        return new ProblemBankResponseDTO(problemBank.getStudyProblemBankPk(),
                problemBank.getBankName(),
                problemBank.getMember().getNickName(),
                problemBankValidator.isManager(memberPk, problemBank));
    }


}
