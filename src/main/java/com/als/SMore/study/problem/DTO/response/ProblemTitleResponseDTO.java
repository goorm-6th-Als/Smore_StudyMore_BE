package com.als.SMore.study.problem.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTitleResponseDTO {
    private Long pk;
    private String writer;
    private String title;
    private LocalDate createDate;

}
