package com.als.SMore.study.todo.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.PersonalTodo;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.TodoStatus;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.PersonalTodoRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.todo.DTO.PersonalTodoDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalTodoService {

    private final PersonalTodoRepository personalTodoRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;

    /**
     * 상태에 따른 모든 PersonalTodo 항목을 조회
     * @param studyPk 스터디 PK
     * @param status  상태
     * @return PersonalTodoDTO 목록
     */
    public List<PersonalTodoDTO> getTodosByStatus(Long studyPk, String status) {
        TodoStatus todoStatus;
        try {
            todoStatus = TodoStatus.fromDisplayName(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("status가 올바르지 않습니다: " + status);
        }
        return personalTodoRepository.findByStudyStudyPkAndScheduleStatus(studyPk, todoStatus).stream()
                .map(PersonalTodoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 새로운 PersonalTodo 항목을 생성
     * @param personalTodoDTO 생성할 PersonalTodoDTO 객체
     * @return 생성된 PersonalTodoDTO 객체
     */
    public PersonalTodoDTO createPersonalTodo(PersonalTodoDTO personalTodoDTO) {

        Study study = studyRepository.findById(personalTodoDTO.getStudyPk())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 스터디 PK: " + personalTodoDTO.getStudyPk()));
        Member member = memberRepository.findById(personalTodoDTO.getMemberPk())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 멤버 PK: " + personalTodoDTO.getMemberPk()));

        if (!isMemberOfStudy(member, study)) {
            throw new IllegalArgumentException("멤버가 스터디에 속해 있지 않습니다.");
        }
        //서비스 계층과 컨트롤러 분리용
        PersonalTodo personalTodo = PersonalTodoDTO.toEntity(personalTodoDTO, member, study);
        personalTodo = personalTodoRepository.save(personalTodo);
        return PersonalTodoDTO.fromEntity(personalTodo);
    }

    /*
     * 멤버가 스터디에 속해 있는지 확인
     * @param member 확인할 멤버
     * @param study 확인할 스터디
     * @return 멤버가 스터디에 속해 있으면 true, 그렇지 않으면 false
     */
    private boolean isMemberOfStudy(Member member, Study study) {
        return studyMemberRepository.existsByStudyAndMember(study, member);
    }

    /**
     * PersonalTodo 항목의 상태를 업데이트
     * @param todoPk 바꿀 PersonalTodo의 PK
     * @param status 새로운 상태
     * @return 업데이트된 PersonalTodoDTO 객체
     */
    public PersonalTodoDTO updatePersonalTodoStatus(Long todoPk, String status) {
        PersonalTodo personalTodo = personalTodoRepository.findById(todoPk)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 TODO PK: " + todoPk));

        TodoStatus newStatus = TodoStatus.fromDisplayName(status);
        personalTodo = personalTodo.updateFields(newStatus, personalTodo.getScheduleContent(), personalTodo.getScheduleDate());
        personalTodo = personalTodoRepository.save(personalTodo);
        return PersonalTodoDTO.fromEntity(personalTodo);
    }

    /**
     * PersonalTodo 항목을 삭제합니다.
     * @param todoPk 삭제할 PersonalTodo의 PK
     */
    public void deletePersonalTodoById(Long todoPk) {
        personalTodoRepository.deleteById(todoPk);
    }
}
