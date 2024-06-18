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
@RequestMapping("/study/{studyPk}/todo")
@RequiredArgsConstructor
public class PersonalTodoController {

    private final PersonalTodoService personalTodoService;

    /**
     *  PersonalTodo 생성
     * @param personalTodoDTO 생성할 PersonalTodoDTO 객체
     * @return 생성된 PersonalTodoDTO 객체와 함께 Created 응답 반환
     */
    @PostMapping
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
            @PathVariable Long studyPk, @RequestParam String status) {
        List<PersonalTodoDTO> todos = personalTodoService.getTodosByStatus(studyPk, status);
        return ResponseEntity.ok(todos);
    }

    /**
     * 스터디 전체 PersonalTodo 조회
     * @param studyPk 스터디 PK
     * @return PersonalTodoDTO 목록과 OK 응답 반환
     */
    @GetMapping
    public ResponseEntity<List<PersonalTodoDTO>> getAllTodos(@PathVariable Long studyPk) {
        List<PersonalTodoDTO> todos = personalTodoService.getAllTodos(studyPk);
        return ResponseEntity.ok(todos);
    }

    /**
     * PersonalTodo 항목의 상태 및 내용을 업데이트
     * @param todoPk 업데이트할 PersonalTodo의 PK
     * @param personalTodoDTO 업데이트할 데이터가 담긴 DTO
     * @return 업데이트된 PersonalTodoDTO 객체와 함께 OK 응답 반환
     */
    @PutMapping("/{todoPk}")
    public ResponseEntity<PersonalTodoDTO> updateTodo(
            @PathVariable Long studyPk, @PathVariable Long todoPk, @RequestBody PersonalTodoDTO personalTodoDTO) {
        PersonalTodoDTO updatedDTO = personalTodoDTO.toBuilder().studyPk(studyPk).build();
        PersonalTodoDTO updatedTodo = personalTodoService.updatePersonalTodo(todoPk, updatedDTO);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * PersonalTodo 삭제
     * @param todoPk 삭제할 PersonalTodo의 PK
     * @return 삭제된 후 No Content 응답 반환
     */
    @DeleteMapping("/{todoPk}")
    public ResponseEntity<Void> deletePersonalTodo(
            @PathVariable Long todoPk) {
        personalTodoService.deletePersonalTodoById(todoPk);
        return ResponseEntity.noContent().build();
    }
}