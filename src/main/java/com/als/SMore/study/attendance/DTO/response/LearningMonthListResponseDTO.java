package com.als.SMore.study.attendance.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearningMonthListResponseDTO {
    private List<LearningMonthResponseDTO> LearningTimeList;
    private Long totalTime;
}
