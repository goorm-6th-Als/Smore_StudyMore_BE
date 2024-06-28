package com.als.SMore.study.todo.DTO;

import com.als.SMore.global.json.LongToStringSerializer;
import com.als.SMore.global.json.StringToLongDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class PersonalTodoDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long personalTodoPk;

    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long studyPk;

    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long memberPk;

    private String scheduleStatus;
    private String scheduleContent;
    private LocalDate modifyDate;
    private LocalDate createDate;
}