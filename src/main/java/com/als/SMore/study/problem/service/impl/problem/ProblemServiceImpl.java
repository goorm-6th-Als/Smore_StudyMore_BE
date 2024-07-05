package com.als.SMore.study.problem.service.impl.problem;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.ProblemOptions;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.domain.repository.ProblemOptionsRepository;
import com.als.SMore.domain.repository.ProblemRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.log.timeTrace.TimeTrace;
import com.als.SMore.study.problem.DTO.request.problem.ProblemCreateRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemGetAllRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemOptionRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemOptionResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemSummaryResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemUpdateResponseDTO;
import com.als.SMore.study.problem.mapper.ProblemMapper;
import com.als.SMore.study.problem.service.ProblemService;
import com.als.SMore.study.problem.validator.ProblemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@TimeTrace
@Slf4j
public class ProblemServiceImpl implements ProblemService {
    private final ProblemValidator problemValidator;
    private final ProblemRepository problemRepository;
    private final ProblemOptionsRepository problemOptionsRepository;

    /**
     * 문제 옵션을 전부 저장한다
     * @param problemOptionRequestDTOList
     * @param answerNum 정답 번호
     * @param problem
     * @return
     */
    private Long saveProblemOptionList(List<ProblemOptionRequestDTO> problemOptionRequestDTOList, Integer answerNum, Problem problem) {
        Long result = 0L;
        List<ProblemOptions> problemOptions = new ArrayList<>();
        //문제 리스트 저장
        for (ProblemOptionRequestDTO problemOptionRequestDTO : problemOptionRequestDTOList) {
            problemOptions.add(ProblemOptions.builder()
                    .optionsContent(problemOptionRequestDTO.getContent())
                    .problem(problem)
                    .optionsNum(problemOptionRequestDTO.getNum())
                    .build());
        }
        problemOptions = problemOptionsRepository.saveAll(problemOptions);
        //정답 저장
        for (ProblemOptions problemOption : problemOptions) {
            if (Objects.equals(problemOption.getOptionsNum(), answerNum)) {
                result = problemOption.getProblemOptionsPk();
                break;
            }
        }
        return result;
    }

    /**
     * 문제 옵션을 전부 지운다
     * @param problem
     */
    private void deleteProblemOptionList(Problem problem) {
        List<ProblemOptions> problemOptions = problemOptionsRepository.findAllByProblemOrderByOptionsNum(problem);
        problemOptionsRepository.deleteAll(problemOptions);
    }


    @Override
    public void createProblem(Long memberPk, ProblemCreateRequestDTO problemCreateRequestDTO) {
        Member member = problemValidator.getMember(memberPk);
        StudyProblemBank problemBank = problemValidator.getStudyProblemBank(problemCreateRequestDTO.getStudyProblemBankPk());

        Problem problem = problemRepository.save(Problem.builder()
                .createDate(LocalDate.now())
                .problemContent(problemCreateRequestDTO.getContent())
                .problemExplanation(problemCreateRequestDTO.getExplanation())
                .member(member)
                .studyProblemBank(problemBank)
                .build());
        //answerPk 컬럼 업데이트 & 문제옵션 리스트 저장
        problem.updateProblemAnswerPk(
                saveProblemOptionList(problemCreateRequestDTO.getProblemOptionRequestDTOList()
                        , problemCreateRequestDTO.getAnswer()
                        , problem
                ));
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProblemSummaryResponseDTO> getAllProblemSummary(Long problemBankPk) {

        StudyProblemBank studyProblemBank = problemValidator.getStudyProblemBank(problemBankPk);

        return problemRepository.findAllProblemSummaryByStudyProblem(studyProblemBank);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProblemResponseDTO> getAllProblem(ProblemGetAllRequestDTO problemGetAllRequestDTO) {
        List<Problem> problems = new ArrayList<>();
        for (Long problemPk : problemGetAllRequestDTO.getStudyProblemBankPk()) {
            StudyProblemBank studyProblemBank = problemValidator.getStudyProblemBank(problemPk);
            problems.addAll(problemRepository.findByStudyProblemBank(studyProblemBank));
        }

        int max = Math.min(100, problems.size());
        max = Math.min(max, problemGetAllRequestDTO.getMax());

        List<ProblemResponseDTO> ResultProblems = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            //난수 생성
            int random = (int) (Math.random() * problems.size());

            // 랜덤 문제 조회
            Problem problem = problems.get(random);

            //랜덤 문제의 선택지들 조회
            List<ProblemOptions> problemOptions = problemOptionsRepository.findAllByProblemOrderByOptionsNum(problem);
            List<ProblemOptionResponseDTO> problemOptionResponseDTO = new ArrayList<>();
            for (ProblemOptions problemOption : problemOptions) {
                problemOptionResponseDTO.add(ProblemMapper.problemOptionsToProblemOptionResponseDTO(problemOption));
            }

            //랜덤 문제와 보기들
            ResultProblems.add(
                    ProblemMapper.problemAndProblemOptionResponseDTOToProblemResponseDTO(problem, problemOptionResponseDTO)
            );

            //선택됐던 문제 지우기
            problems.remove(random);
        }

        return ResultProblems;
    }

    @Override
    @Transactional
    public ProblemUpdateResponseDTO getProblem(Long problemPk) {
        Problem problem = problemValidator.getProblem(problemPk);
        List<ProblemOptions> problemOptions = problemOptionsRepository.findAllByProblemOrderByOptionsNum(problem);
        List<ProblemOptionResponseDTO> problemOptionResponseDTOList = new ArrayList<>();
        for (ProblemOptions problemOption : problemOptions) {
            problemOptionResponseDTOList.add(ProblemMapper.problemOptionsToProblemOptionResponseDTO(problemOption));
        }
        return ProblemMapper.problemAndProblemOptionResponseDTOToProblemUpdateResponseDTO(problem, problemOptionResponseDTOList);
    }

    @Override
    public void updateProblem(ProblemUpdateRequestDTO problemUpdateRequestDTO, Long memberPk) {



        Problem problem = problemValidator.getProblem(problemUpdateRequestDTO.getProblemPk());

        if (!problemValidator.isManager(memberPk, problem.getStudyProblemBank()))
            throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
        ;

        List<ProblemOptions> problemOptions = problemOptionsRepository.findAllByProblemOrderByOptionsNum(problem);
        problem.updateAll(problemUpdateRequestDTO);
        //문제옵션들 싹 다 지우고
        deleteProblemOptionList(problem);
        //다시 저장
        Long answerPk = saveProblemOptionList(
                problemUpdateRequestDTO.getProblemOptionRequestDTOList()
                , problemUpdateRequestDTO.getAnswer()
                , problem
        );
        //정답도 다시 업데이트
        problem.updateProblemAnswerPk(answerPk);

    }

    @Override
    public void deleteProblem(Long problemPk, Long memberPk) {

        Problem problem = problemValidator.getProblem(problemPk);

        if (!problemValidator.isManager(memberPk, problem.getStudyProblemBank()))
            throw new CustomException(CustomErrorCode.UNAUTHORIZED_ACCESS);
        deleteProblemOptionList(problem);
        problemRepository.delete(problem);

    }


}
