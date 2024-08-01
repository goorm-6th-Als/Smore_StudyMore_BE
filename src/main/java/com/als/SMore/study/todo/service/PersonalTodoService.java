package com.als.SMore.study.todo.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.PersonalTodo;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.TodoStatus;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.PersonalTodoRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.study.todo.DTO.PersonalTodoDTO;
import com.als.SMore.study.todo.DTO.PersonalTodoWithStatusDTO;
import com.als.SMore.study.todo.mapper.PersonalTodoMapper;
import com.als.SMore.user.login.util.MemberUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonalTodoService {
    private static final Logger logger = LoggerFactory.getLogger(PersonalTodoService.class);
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
        Long memberPk = MemberUtil.getUserPk();
        Study study = validateStudy(studyPk);
        Member member = memberRepository.findById(memberPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER));

        if (!isMemberOfStudy(member, study)) {
            throw new CustomException(CustomErrorCode.NOT_STUDY_MEMBER);
        }
        // status가 null이면 "진행 전"을 디폴트로 넣어 줌
        TodoStatus scheduleStatus = personalTodoDTO.getScheduleStatus() != null ?
                validateTodoStatus(personalTodoDTO.getScheduleStatus()) : TodoStatus.NOT_STARTED;


        PersonalTodo personalTodo = PersonalTodoMapper.toEntity(personalTodoDTO, member, study, scheduleStatus);
        personalTodo = personalTodoRepository.save(personalTodo);
        return PersonalTodoMapper.fromEntity(personalTodo);
    }


    /**
     * 스터디 전체 Todo 조회
     *
     * @param studyPk 스터디 PK
     * @return PersonalTodoDTO 목록
     */
    @Transactional(readOnly = true)
    public List<PersonalTodoDTO> getAllTodos(Long studyPk) {
        validateStudy(studyPk);
        return personalTodoRepository.findByStudyStudyPk(studyPk).stream()
                .map(PersonalTodoMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 Todo 조회
     *
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

        return PersonalTodoMapper.fromEntity(personalTodo);
    }


    /**
     * 상태에 따른 모든 PersonalTodo 항목을 조회
     *
     * @param studyPk 스터디 PK
     * @param status  상태
     * @return PersonalTodoDTO 목록
     */
    @Transactional(readOnly = true)
    public List<PersonalTodoWithStatusDTO> getTodosByStatus(Long studyPk, String status) {
        validateStudy(studyPk);
        TodoStatus todoStatus = validateTodoStatus(status);
        return personalTodoRepository.findByStudyStudyPkAndScheduleStatus(studyPk, todoStatus).stream()
                .map(personalTodo -> {
                    Member member = personalTodo.getMember();
                    return PersonalTodoMapper.toDTO(personalTodo, member);
                })
                .collect(Collectors.toList());
    }

    /**
     * 특정 멤버의 모든 PersonalTodo 항목을 조회
     * @param studyPk 스터디 PK
     * @return PersonalTodoDTO 목록
     */
    @Transactional(readOnly = true)
    public List<PersonalTodoDTO> getTodosByMember(Long studyPk) {
        Long memberPk = MemberUtil.getUserPk();
        Study study = validateStudy(studyPk);
        Member member = memberRepository.findById(memberPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER));
        if (!isMemberOfStudy(member, study)) {
            throw new CustomException(CustomErrorCode.NOT_STUDY_MEMBER);
        }
        return personalTodoRepository.findByMemberMemberPk(memberPk).stream()
                .map(PersonalTodoMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * PersonalTodo 항목을 업데이트, 작성자만 가능
     *
     * @param studyPk         스터디 PK
     * @param todoPk          업데이트할 PersonalTodo의 PK
     * @param personalTodoDTO 업데이트할 PersonalTodoDTO 객체
     * @return 업데이트된 PersonalTodoDTO 객체
     */
    @Transactional
    public PersonalTodoDTO updatePersonalTodo(Long studyPk, Long todoPk, PersonalTodoDTO personalTodoDTO) {
        Long memberPk = MemberUtil.getUserPk();
        validateStudy(studyPk);

        PersonalTodo personalTodo = personalTodoRepository.findById(todoPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_TODO));

        if (!personalTodo.getMember().getMemberPk().equals(memberPk)) {
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_MEMBER);
        }

        TodoStatus scheduleStatus = validateTodoStatus(personalTodoDTO.getScheduleStatus());

        personalTodo = PersonalTodoMapper.updateEntity(personalTodoDTO, personalTodo, scheduleStatus);
        personalTodo = personalTodoRepository.save(personalTodo);
        return PersonalTodoMapper.fromEntity(personalTodo);
    }

    /**
     * PersonalTodo 항목을 삭제, 작성자와 스터디 장만 가능
     *
     * @param todoPk 삭제할 PersonalTodo의 PK
     */
    @Transactional
    public void deletePersonalTodoById(Long todoPk) {
        Long memberPk = MemberUtil.getUserPk();
        PersonalTodo personalTodo = personalTodoRepository.findById(todoPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_TODO));

        if (!personalTodo.getMember().getMemberPk().equals(memberPk) &&
                !isAdmin(personalTodo.getStudy().getStudyPk(), memberPk)) {
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_MEMBER);
        }

        personalTodoRepository.deleteById(todoPk);
    }

    private boolean isAdmin(Long studyPk, Long memberPk) {
        return studyMemberRepository.existsByStudyStudyPkAndMemberMemberPkAndRole(studyPk, memberPk, "admin");
    }

    private Study validateStudy(Long studyPk) {
        return studyRepository.findById(studyPk)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));
    }

    private boolean isMemberOfStudy(Member member, Study study) {
        return studyMemberRepository.existsByStudyAndMember(study, member);
    }

    private TodoStatus validateTodoStatus(String status) {
        try {
            return TodoStatus.fromDisplayName(status);
        } catch (IllegalArgumentException e) {
            logger.error("불가능한 status: {}", status);
            throw new CustomException(CustomErrorCode.INVALID_STATUS);
        }
    }
}
