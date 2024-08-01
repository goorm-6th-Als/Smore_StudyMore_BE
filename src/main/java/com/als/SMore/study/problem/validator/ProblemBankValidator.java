package com.als.SMore.study.problem.validator;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.domain.repository.MemberRepository;
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
public class ProblemBankValidator {

    private final StudyProblemBankRepository studyProblemBankRepository;

    // 문제은행 제목 글자 수 1 - 30 제한
    public void bankNameLength(String bankName) {
        if(bankName == null || bankName.length() > 30 || bankName.isBlank()) {
            throw new CustomException(CustomErrorCode.INVALID_TITLE_BANK_NAME);
        }
    }

    // 문제은행 수 30개 제한
    public void studyProblemBankSize(Study study) {
        long count = studyProblemBankRepository.countByStudy(study);
        log.info("count = {}",count);
        if(count > 29L) throw new CustomException(CustomErrorCode.PROBLEM_BANK_COUNT_OVER_LIMIT);
    }

    // 수정, 삭제 권한은 작성자나 방장만 있다
    public boolean isManager(Long memberPk, StudyProblemBank studyProblemBank) {
        return Objects.equals(studyProblemBank.getMember().getMemberPk(), memberPk) // 작성자 거나
                || Objects.equals(studyProblemBank.getStudy().getMember().getMemberPk(), memberPk); //방장 이거나
    }

    //
    public StudyProblemBank getProblemBank(Long problemBankPk) {
        Optional<StudyProblemBank> optionalStudyProblemBank = studyProblemBankRepository.findById(problemBankPk);
        if (optionalStudyProblemBank.isPresent()) {
            return studyProblemBankRepository.findById(problemBankPk).get();
        }
        else throw new CustomException(CustomErrorCode.NOT_FOUND_PROBLEM_BANK);
    }

}
