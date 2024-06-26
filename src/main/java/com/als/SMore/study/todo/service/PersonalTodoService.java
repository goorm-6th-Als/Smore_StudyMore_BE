package com.als.SMore.study.todo.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.PersonalTodo;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.TodoStatus;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.PersonalTodoRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.todo.DTO.PersonalTodoDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonalTodoService {

    private final PersonalTodoRepository personalTodoRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;

    /**
     * 새로운 PersonalTodo 항목을 생성
     *
     * @param personalTodoDTO 생성할 PersonalTodoDTO 객체
     * @param studyPk         스터디 PK
     * @return 생성된 PersonalTodoDTO 객체
     */
    public PersonalTodoDTO createPersonalTodo(PersonalTodoDTO personalTodoDTO, Long studyPk) {

        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));
        Member member = memberRepository.findById(personalTodoDTO.getMemberPk())
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER));

        if (!isMemberOfStudy(member, study)) {
            throw new CustomException(CustomErrorCode.NOT_STUDY_MEMBER);
        }

        PersonalTodo personalTodo = PersonalTodoDTO.toEntity(personalTodoDTO, member, study);
        personalTodo = personalTodoRepository.save(personalTodo);
        return PersonalTodoDTO.fromEntity(personalTodo);
    }

    /**
     * 스터디 전체 Todo 조회
     * @param studyPk 스터디 PK
     * @return PersonalTodoDTO 목록
     */
    public List<PersonalTodoDTO> getAllTodos(Long studyPk) {
        return personalTodoRepository.findByStudyStudyPk(studyPk).stream()
                .map(PersonalTodoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 Todo 조회
     * @param studyPk 스터디 PK
     * @param todoPk  Todo PK
     * @return PersonalTodoDTO 객체
     */
    @Transactional(readOnly = true)
    public PersonalTodoDTO getTodoDetail(Long studyPk, Long todoPk) {
        PersonalTodo personalTodo = personalTodoRepository.findById(todoPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_TODO));

        if (!personalTodo.getStudy().getStudyPk().equals(studyPk)) {
            throw new CustomException(CustomErrorCode.NOT_FOUND_STUDY);
        }

        return PersonalTodoDTO.fromEntity(personalTodo);
    }


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
            throw new CustomException(CustomErrorCode.INVALID_STATUS);
        }
        return personalTodoRepository.findByStudyStudyPkAndScheduleStatus(studyPk, todoStatus).stream()
                .map(PersonalTodoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * PersonalTodo 항목을 업데이트, 작성자만 가능
     * @param todoPk          업데이트할 PersonalTodo의 PK
     * @param personalTodoDTO 업데이트할 PersonalTodoDTO 객체
     * @return 업데이트된 PersonalTodoDTO 객체
     */
    @Transactional
    public PersonalTodoDTO updatePersonalTodo(Long todoPk, PersonalTodoDTO personalTodoDTO, Long memberPk) {
        PersonalTodo personalTodo = personalTodoRepository.findById(todoPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_TODO));

        if (!personalTodo.getMember().getMemberPk().equals(memberPk)) {
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_TODO);
        }

        personalTodo = personalTodoDTO.updateEntity(personalTodo);
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
     * PersonalTodo 항목을 삭제, 작성자와 스터디 관리자만 가능
     *
     * @param todoPk   삭제할 PersonalTodo의 PK
     * @param memberPk 멤버 PK
     */
    @Transactional
    public void deletePersonalTodoById(Long todoPk, Long memberPk) {
        PersonalTodo personalTodo = personalTodoRepository.findById(todoPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_TODO));

        if (!personalTodo.getMember().getMemberPk().equals(memberPk) &&
                !isAdmin(personalTodo.getStudy().getStudyPk(), memberPk)) {
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_TODO);
        }

        personalTodoRepository.deleteById(todoPk);
    }

    /**
     * 멤버가 스터디의 장인지 확인
     *
     * @param studyPk  스터디 PK
     * @param memberPk 멤버 PK
     * @return 멤버가 스터디의 관리자일때 true
     */
    @Transactional(readOnly = true)
    public boolean isAdmin(Long studyPk, Long memberPk) {
        return studyMemberRepository.existsByStudyStudyPkAndMemberMemberPkAndRole(studyPk, memberPk, "admin");
    }

}
