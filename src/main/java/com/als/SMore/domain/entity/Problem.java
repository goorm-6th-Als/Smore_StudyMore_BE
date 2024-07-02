package com.als.SMore.domain.entity;

import com.als.SMore.study.problem.DTO.request.problem.ProblemUpdateRequestDTO;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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
}
