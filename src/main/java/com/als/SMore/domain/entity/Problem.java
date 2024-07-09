package com.als.SMore.domain.entity;

import com.als.SMore.study.problem.DTO.request.problem.ProblemCreateRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemUpdateRequestDTO;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problem")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id @Tsid
    @Column(name = "problem_pk")
    private Long problemPk;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "study_problem_bank_pk", nullable = false)
    private StudyProblemBank studyProblemBank;

    // Cascade 삭제조건 추가.
    @OneToMany(mappedBy = "problem", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProblemOptions> problemOptions;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProblemType> problemTypes;

    @Column(name = "problem_answer_pk")
    private Long problemAnswerPk;

    @Column(name = "problem_content")
    private String problemContent;

    @Column(name = "problem_explanation")
    private String problemExplanation;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private LocalDate createDate;

    public void updateAll(ProblemUpdateRequestDTO dto){
        this.problemContent = dto.getProblemContent();
        this.problemExplanation = dto.getProblemExplanation();
    }

    public void updateProblemAnswerPk(Long problemAnswerPk){
        this.problemAnswerPk = problemAnswerPk;
    }

    public static Problem of(Member member, StudyProblemBank problemBank, ProblemCreateRequestDTO problemCreateRequestDTO){
        return Problem.builder()
                .createDate(LocalDate.now())
                .problemContent(problemCreateRequestDTO.getContent())
                .problemExplanation(problemCreateRequestDTO.getExplanation())
                .member(member)
                .studyProblemBank(problemBank)
                .build();
    }
}
