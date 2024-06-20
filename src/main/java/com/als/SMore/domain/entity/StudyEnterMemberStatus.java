package com.als.SMore.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyEnterMemberStatus {
    PENDING("대기 중"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String displayName;

    @JsonCreator
    public static StudyEnterMemberStatus fromDisplayName(String displayName) {
        for (StudyEnterMemberStatus status : values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 상태입니다: " + displayName);
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
