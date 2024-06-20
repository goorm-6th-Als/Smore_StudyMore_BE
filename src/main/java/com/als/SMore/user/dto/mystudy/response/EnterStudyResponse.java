package com.als.SMore.user.dto.mystudy.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EnterStudyResponse {
    private List<EnterStudy> enterStudyList;
}
