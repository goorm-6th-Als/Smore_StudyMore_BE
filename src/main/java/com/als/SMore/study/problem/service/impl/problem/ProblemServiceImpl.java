package com.als.SMore.study.problem.service.impl.problem;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.ProblemOptions;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.domain.repository.ProblemOptionsRepository;
import com.als.SMore.domain.repository.ProblemRepository;
import com.als.SMore.log.timeTrace.TimeTrace;
import com.als.SMore.study.problem.DTO.request.problem.ProblemCreateRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemGetAllRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemOptionResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemSummaryResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemUpdateResponseDTO;
import com.als.SMore.study.problem.service.ProblemService;
import com.als.SMore.study.problem.validator.ProblemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@TimeTrace
@Slf4j
public class ProblemServiceImpl implements ProblemService {
    private final ProblemValidator problemValidator;
    private final ProblemRepository problemRepository;
    private final ProblemOptionsRepository problemOptionsRepository;

    private ProblemOptionResponseDTO problemOptionsToProblemOptionResponseDTO(ProblemOptions problemOptions){
        return ProblemOptionResponseDTO.builder()
                .problemOptionPk(problemOptions.getProblemOptionsPk())
                .content(problemOptions.getOptionsContent())
                .num(problemOptions.getOptionsNum())
                .build();
    }

    private ProblemResponseDTO problemAndProblemOptionResponseDTOToProblemResponseDTO(Problem problem, List<ProblemOptionResponseDTO> problemOptionResponseDTOList){
        return ProblemResponseDTO.builder()
                .problemPk(problem.getProblemPk())
                .memberNickname(problem.getMember().getNickName())
                .studyBankName(problem.getStudyProblemBank().getBankName())
                .problemTitle(problem.getProblemTitle())
                .problemContent(problem.getProblemContent())
                .problemExplanation(problem.getProblemExplanation())
                .problemDate(problem.getCreateDate())
                .options(problemOptionResponseDTOList)
                .build();
    }
    private ProblemUpdateResponseDTO problemAndProblemOptionResponseDTOToProblemUpdateResponseDTO(Problem problem, List<ProblemOptionResponseDTO> problemOptionResponseDTOList){
        return ProblemUpdateResponseDTO.builder()
                .problemPk(problem.getProblemPk())
                .memberNickname(problem.getMember().getNickName())
                .studyBankName(problem.getStudyProblemBank().getBankName())
                .problemTitle(problem.getProblemTitle())
                .problemContent(problem.getProblemContent())
                .problemExplanation(problem.getProblemExplanation())
                .problemDate(problem.getCreateDate())
                .answerPk(problem.getProblemAnswerPk())
                .options(problemOptionResponseDTOList)
                .build();
    }



    @Override
    public void createProblem(Long memberPk, ProblemCreateRequestDTO problemCreateRequestDTO) {
        Member member = problemValidator.getMember(memberPk);
        StudyProblemBank problemBank = problemValidator.getStudyProblemBank(problemCreateRequestDTO.getStudyProblemBankPk());

        problemRepository.save(Problem.builder()
                .createDate(LocalDate.now())
                .problemContent(problemCreateRequestDTO.getContent())
                .problemTitle(problemCreateRequestDTO.getTitle())
                .problemExplanation(problemCreateRequestDTO.getExplanation())
                .member(member)
                .studyProblemBank(problemBank)
                .build());
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
        for (Long problemPk : problemGetAllRequestDTO.getPk()) {
            StudyProblemBank studyProblemBank = problemValidator.getStudyProblemBank(problemPk);
            problems.addAll(problemRepository.findByStudyProblemBank(studyProblemBank));
        }

        int max = Math.min(100, problems.size());
        max = Math.min(max, problemGetAllRequestDTO.getMax());

        List<ProblemResponseDTO> ResultProblems = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            //난수 생성
            int random = (int) (Math.random() *  problems.size());

            // 랜덤 문제 조회
            Problem problem= problems.get(random);

            //랜덤 문제의 선택지들 조회
            List<ProblemOptions> problemOptions = problemOptionsRepository.findAllByProblemOrderByOptionsNum(problem);
            List<ProblemOptionResponseDTO> problemOptionResponseDTO = new ArrayList<>();
            for (ProblemOptions problemOption : problemOptions) {
                problemOptionResponseDTO.add(problemOptionsToProblemOptionResponseDTO(problemOption));
            }

            //랜덤 문제와 보기들
            ResultProblems.add(
                    problemAndProblemOptionResponseDTOToProblemResponseDTO(problem, problemOptionResponseDTO)
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
            problemOptionResponseDTOList.add(problemOptionsToProblemOptionResponseDTO(problemOption));
        }
        return problemAndProblemOptionResponseDTOToProblemUpdateResponseDTO(problem, problemOptionResponseDTOList);
    }

    @Override
    public void updateProblem(ProblemUpdateRequestDTO problemUpdateRequestDTO, Long memberPk) {

        Problem problem = problemValidator.getProblem(problemUpdateRequestDTO.getProblemPk());

        problemValidator.isManager(memberPk, problem.getStudyProblemBank());

        List<ProblemOptions> problemOptions = problemOptionsRepository.findAllByProblemOrderByOptionsNum(problem);

        problem.updateAll(problemUpdateRequestDTO);
        for (int i = 0; i <problemOptions.size(); i++) {
            problemOptions.get(i).updateAll(problemUpdateRequestDTO.getProblemOptionResponseDTOList().get(i));
        }
    }

    @Override
    public void deleteProblem(Long problemPk, Long memberPk) {

        Problem problem = problemValidator.getProblem(problemPk);

        problemValidator.isManager(memberPk, problem.getStudyProblemBank());

        problemRepository.delete(problem);

    }


}
