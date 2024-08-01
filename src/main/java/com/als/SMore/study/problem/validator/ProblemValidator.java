package com.als.SMore.study.problem.validator;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.ProblemRepository;
import com.als.SMore.domain.repository.StudyProblemBankRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProblemValidator {
    private final ProblemRepository problemRepository;
    private final StudyProblemBankRepository studyProblemBankRepository;


    public StudyProblemBank getStudyProblemBank(Long studyProblemBankPk) {
        // studyProblemBank Pk를 받아서 검증
        Optional<StudyProblemBank> problemBank = studyProblemBankRepository.findById(studyProblemBankPk);
        return problemBank.orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_PROBLEM_BANK));
    }

    public Problem getProblem(Long ProblemPk){
        Optional<Problem> problem = problemRepository.findById(ProblemPk);
        return problem.orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_PROBLEM));
    }

    // 수정, 삭제 권한은 작성자나 방장만 있다
    public boolean isManager(Long memberPk, StudyProblemBank studyProblemBank) {
        return Objects.equals(studyProblemBank.getMember().getMemberPk(), memberPk) // 작성자 거나
                || Objects.equals(studyProblemBank.getStudy().getMember().getMemberPk(), memberPk); //방장 이거나
    }

}
