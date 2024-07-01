package com.als.SMore.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TodoStatus {
    NOT_STARTED("진행 전", "NOT_STARTED"),
    IN_PROGRESS("진행 중", "IN_PROGRESS"),
    COMPLETED("완료", "COMPLETED");

    private final String displayName;
    @Getter
    private final String englishName;

    @JsonCreator
    public static TodoStatus fromDisplayName(String displayName) {
        for (TodoStatus status : values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 상태입니다. : " + displayName);
    }

    public static TodoStatus fromEnglishName(String englishName) {
        for (TodoStatus status : values()) {
            if (status.englishName.equals(englishName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 상태입니다. : " + englishName);
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

}