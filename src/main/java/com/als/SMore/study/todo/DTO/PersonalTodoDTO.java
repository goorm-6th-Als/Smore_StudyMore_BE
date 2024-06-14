package com.als.SMore.study.todo.DTO;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.PersonalTodo;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.TodoStatus;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class PersonalTodoDTO {
    private Long personalTodoPk;
    private Long studyPk;
    private Long memberPk;
    private TodoStatus scheduleStatus;
    private String scheduleContent;
    private LocalDate modifyDate;
    private LocalDate createDate;

    // PersonalTodo 랑 PersonalTodoDTO 개체 변환용
    public static PersonalTodoDTO fromEntity(PersonalTodo personalTodo) {
        return PersonalTodoDTO.builder()
                .personalTodoPk(personalTodo.getPersonalTodoPk())
                .studyPk(personalTodo.getStudy().getStudyPk())
                .memberPk(personalTodo.getMember().getMemberPk())
                .scheduleStatus(personalTodo.getScheduleStatus())
                .scheduleContent(personalTodo.getScheduleContent())
                .modifyDate(personalTodo.getModifyDate())
                .createDate(personalTodo.getCreateDate())
                .build();
    }

    public static PersonalTodo toEntity(PersonalTodoDTO personalTodoDTO, Member member, Study study) {
        return PersonalTodo.builder()
                .personalTodoPk(personalTodoDTO.getPersonalTodoPk())
                .member(member)
                .study(study)
                .scheduleStatus(personalTodoDTO.getScheduleStatus())
                .scheduleContent(personalTodoDTO.getScheduleContent())
                .modifyDate(LocalDate.now())
                .createDate(personalTodoDTO.getCreateDate())
                .build();
    }

    public PersonalTodo updateEntity(PersonalTodo personalTodo) {
        return personalTodo.toBuilder()
                .scheduleStatus(this.scheduleStatus != null ? this.scheduleStatus : personalTodo.getScheduleStatus())
                .scheduleContent(this.scheduleContent != null ? this.scheduleContent : personalTodo.getScheduleContent())
                .modifyDate(LocalDate.now())
                .build();
    }
}
