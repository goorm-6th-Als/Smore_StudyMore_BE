package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.PersonalTodo;
import com.als.SMore.domain.entity.TodoStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalTodoRepository extends JpaRepository<PersonalTodo, Long> {
    List<PersonalTodo> findByStudyStudyPkAndScheduleStatus(Long studyPk, TodoStatus scheduleStatus);
    List<PersonalTodo> findByStudyStudyPk(Long studyPk);
}
