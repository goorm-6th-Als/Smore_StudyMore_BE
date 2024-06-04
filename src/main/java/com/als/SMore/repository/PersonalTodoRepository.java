package com.als.SMore.repository;

import com.als.SMore.entity.PersonalTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalTodoRepository extends JpaRepository<PersonalTodo, Long> {
}
