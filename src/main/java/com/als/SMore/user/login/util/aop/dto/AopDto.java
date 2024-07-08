package com.als.SMore.user.login.util.aop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AopDto {
    private String studyPk;
    private String token;
}
