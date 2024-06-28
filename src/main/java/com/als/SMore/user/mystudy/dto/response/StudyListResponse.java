package com.als.SMore.user.mystudy.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class StudyListResponse {
    private List<StudyResponse> studyList;
}
