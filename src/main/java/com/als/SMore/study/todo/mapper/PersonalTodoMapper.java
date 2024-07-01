package com.als.SMore.study.todo.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.PersonalTodo;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.TodoStatus;
import com.als.SMore.study.todo.DTO.PersonalTodoDTO;
import java.time.LocalDate;

public class PersonalTodoMapper {

    public static PersonalTodoDTO fromEntity(PersonalTodo personalTodo) {
        return PersonalTodoDTO.builder()
                .personalTodoPk(personalTodo.getPersonalTodoPk())
                .studyPk(personalTodo.getStudy().getStudyPk())
                .memberPk(personalTodo.getMember().getMemberPk())
                .scheduleStatus(personalTodo.getScheduleStatus().getDisplayName())  // 한글 상태로 변환
                .scheduleContent(personalTodo.getScheduleContent())
                .modifyDate(personalTodo.getModifyDate())
                .createDate(personalTodo.getCreateDate())
                .build();
    }

    public static PersonalTodo toEntity(PersonalTodoDTO personalTodoDTO, Member member, Study study, TodoStatus status) {
        return PersonalTodo.builder()
                .personalTodoPk(personalTodoDTO.getPersonalTodoPk())
                .member(member)
                .study(study)
                .scheduleStatus(status)
                .scheduleContent(personalTodoDTO.getScheduleContent())
                .modifyDate(LocalDate.now())
                .createDate(LocalDate.now())
                .build();
    }


    public static PersonalTodo updateEntity(PersonalTodoDTO personalTodoDTO, PersonalTodo personalTodo, TodoStatus status) {
        return personalTodo.toBuilder()
                .scheduleStatus(status != null ? status : personalTodo.getScheduleStatus())
                .scheduleContent(personalTodoDTO.getScheduleContent() != null ? personalTodoDTO.getScheduleContent() : personalTodo.getScheduleContent())
                .modifyDate(LocalDate.now())
                .build();
    }
}
