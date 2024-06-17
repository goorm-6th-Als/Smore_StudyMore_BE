package com.als.SMore.study.attendance.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearningMonthResponseDTO {
    private LocalDate date;
    private LocalTime time;
}
