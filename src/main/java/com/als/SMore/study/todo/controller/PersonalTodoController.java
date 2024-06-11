package com.als.SMore.study.todo.controller;

import com.als.SMore.study.todo.DTO.PersonalTodoDTO;
import com.als.SMore.study.todo.service.PersonalTodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class PersonalTodoController {

    private final PersonalTodoService personalTodoService;

    /**
     *  PersonalTodo 생성
     * @param personalTodoDTO 생성할 PersonalTodoDTO 객체
     * @return 생성된 PersonalTodoDTO 객체와 함께 Created 응답 반환
     */
    @PostMapping("/create")
    public ResponseEntity<PersonalTodoDTO> createPersonalTodo(
            @RequestBody PersonalTodoDTO personalTodoDTO) {
        PersonalTodoDTO createdTodo = personalTodoService.createPersonalTodo(personalTodoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * 상태에 따른 PersonalTodo 조회
     * @param studyPk 스터디 PK
     * @param status  상태
     * @return 상태에 따른 PersonalTodoDTO 목록과 함께 OK 응답 반환
     */
    @GetMapping("/status")
    public ResponseEntity<List<PersonalTodoDTO>> getTodosByStatus(
            @RequestParam Long studyPk, @RequestParam String status) {
        List<PersonalTodoDTO> todos = personalTodoService.getTodosByStatus(studyPk, status);
        return ResponseEntity.ok(todos);
    }

    /**
     * PersonalTodo 상태 업데이트
     * @param todoPk 업데이트할 PersonalTodo의 PK
     * @param status 새로운 상태 문자열
     * @return 업데이트된 PersonalTodoDTO 객체와 함께 OK 응답 반환
     */
    @PutMapping("/update/{todoPk}")
    public ResponseEntity<PersonalTodoDTO> updateTodoStatus(
            @PathVariable Long todoPk, @RequestParam String status) {
        PersonalTodoDTO updatedTodo = personalTodoService.updatePersonalTodoStatus(todoPk, status);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * PersonalTodo 삭제
     * @param todoPk 삭제할 PersonalTodo의 PK
     * @return 삭제된 후 No Content 응답 반환
     */
    @DeleteMapping("/delete/{todoPk}")
    public ResponseEntity<Void> deletePersonalTodo(
            @PathVariable Long todoPk) {
        personalTodoService.deletePersonalTodoById(todoPk);
        return ResponseEntity.noContent().build();
    }
}
