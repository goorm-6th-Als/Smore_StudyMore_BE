package com.als.SMore.study.problem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DTOAndPermission<T> {
    List<T> DTOList;
    boolean permission;
}
