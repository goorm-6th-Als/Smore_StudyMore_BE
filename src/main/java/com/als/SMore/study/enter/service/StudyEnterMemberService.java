package com.als.SMore.study.enter.service;

import static com.als.SMore.global.CustomErrorCode.CustomErrorCode;
import static com.als.SMore.global.CustomErrorCode.NOT_AUTHORIZED_REQUEST_MEMBER;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyEnterMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyEnterMemberRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomException;
import com.als.SMore.notification.dto.NotificationRequestDto;
import com.als.SMore.notification.service.NotificationService;
import com.als.SMore.study.enter.DTO.StudyEnterMemberDTO;
import com.als.SMore.study.enter.DTO.StudyEnterMemberWithMemberInfoDTO;
import com.als.SMore.study.enter.DTO.StudyEnterMemberWithStudyInfoDTO;
import com.als.SMore.study.enter.mapper.StudyEnterMemberMapper;
import com.als.SMore.study.studyCRUD.service.StudyService;
import com.als.SMore.user.login.util.MemberUtil;
import com.als.SMore.user.login.util.aop.annotation.NotAop;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyEnterMemberService {
    private final StudyService studyService;
    private final StudyEnterMemberRepository studyEnterMemberRepository;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final NotificationService notificationService;


    /**
     * 스터디 가입신청 생성
     * @param studyEnterMemberDTO StudyEnterMemberDTO 객체
     * @param studyPk 스터디 PK
     * @return StudyEnterMemberDTO 객체
     */
    @NotAop
    @Transactional
    public StudyEnterMemberDTO createStudyEnterMember(StudyEnterMemberDTO studyEnterMemberDTO, Long studyPk) {
        Long memberPk = MemberUtil.getUserPk();  // 토큰에서 memberPk 가져오기
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 ID: " + studyPk));
        Member member = memberRepository.findById(memberPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 ID: " + memberPk));

        // 스터디 멤버인지, 스터디에 신청했는지 판단
        if (studyMemberRepository.existsByStudyAndMember(study, member)) {
            throw new CustomException(CustomErrorCode.ALREADY_MEMBER);
        }
        if (studyEnterMemberRepository.existsByStudyAndMember(study, member)) {
            throw new CustomException(CustomErrorCode.ALREADY_APPLIED);
        }

        // 최대 스터디 참여 검증
        studyService.validateMaxEnterStudy(memberPk);

        StudyEnterMember studyEnterMember = StudyEnterMemberMapper.toEntity(studyEnterMemberDTO, study, member);
        studyEnterMember = studyEnterMemberRepository.save(studyEnterMember);
        //스터디 방장에게 "스터디 가입 신청 요청이 있습니다." 알림
        notify(study.getMember().getMemberPk(), studyPk,"스터디 가입 신청 요청이 있습니다.");
        return StudyEnterMemberMapper.toDTO(studyEnterMember);
    }

    /**
     * 특정 스터디의 모든 가입 신청서 조회
     * @param studyPk 스터디 PK
     * @return 해당 스터디의 모든 StudyEnterMemberDTO 리스트
     */
    @NotAop
    @Transactional(readOnly = true)
    public List<StudyEnterMemberWithMemberInfoDTO> getAllStudyEnterMembers(Long studyPk) {
        return studyEnterMemberRepository.findByStudyStudyPk(studyPk).stream()
                .map(StudyEnterMemberMapper::toDTOWithMemberInfo)
                .collect(Collectors.toList());
    }

    /**
     * 나의 모든 가입 신청서 조회
     * @return 해당 멤버의 모든 StudyEnterMemberDTO 리스트
     */
    @NotAop
    @Transactional(readOnly = true)
    public List<StudyEnterMemberWithStudyInfoDTO> getAllStudyEnterMembersByMember() {
        Long memberPk = MemberUtil.getUserPk();
        return studyEnterMemberRepository.findByMemberMemberPk(memberPk).stream()
                .map(StudyEnterMemberMapper::toDTOWithStudyInfo)
                .collect(Collectors.toList());
    }

    /**
     *  스터디 가입 신청서 수정
     * @param studyEnterMemberPk 수정할 StudyEnterMember의 PK
     * @param studyEnterMemberDTO 수정할 내용이 담긴 StudyEnterMemberDTO 객체
     * @return 수정된 StudyEnterMemberDTO 객체
     */
    @NotAop
    @Transactional
    public StudyEnterMemberDTO updateStudyEnterMember(Long studyEnterMemberPk, StudyEnterMemberDTO studyEnterMemberDTO) {
        StudyEnterMember studyEnterMember = studyEnterMemberRepository.findById(studyEnterMemberPk)
                .orElseThrow(() -> new IllegalArgumentException("가입 신청서가 존재하지 않습니다: " + studyEnterMemberPk));
        studyEnterMember = StudyEnterMemberMapper.updateEntity(studyEnterMemberDTO, studyEnterMember);
        studyEnterMember = studyEnterMemberRepository.save(studyEnterMember);
        return StudyEnterMemberMapper.toDTO(studyEnterMember);
    }

    /**
     *  스터디 가입 신청서 삭제
     * @param studyEnterMemberPk 삭제할 StudyEnterMember의 PK
     */
    @NotAop
    @Transactional
    public void deleteStudyEnterMember(Long studyEnterMemberPk) {
        Long memberPk = MemberUtil.getUserPk();
        StudyEnterMember studyEnterMember = studyEnterMemberRepository.findById(studyEnterMemberPk)
                .orElseThrow(() -> new CustomException(NOT_AUTHORIZED_REQUEST_MEMBER));

        // 삭제하려는 사용자가 본인인지 확인
        if (!studyEnterMember.getMember().getMemberPk().equals(memberPk)) {
            throw new CustomException(NOT_AUTHORIZED_REQUEST_MEMBER);
        }
        studyEnterMemberRepository.deleteById(studyEnterMemberPk);
    }

    private void notify(Long receiverPk, Long studyPk, String content) {
        NotificationRequestDto notificationRequest = NotificationRequestDto.builder()
                .receiverPk(receiverPk)
                .studyPk(studyPk)
                .content(content)
                .build();
        notificationService.send(notificationRequest);
    }
}
