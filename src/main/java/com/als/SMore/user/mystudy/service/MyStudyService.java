package com.als.SMore.user.mystudy.service;

import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.*;
import com.als.SMore.notification.dto.NotificationRequestDto;
import com.als.SMore.notification.service.NotificationService;
import com.als.SMore.user.login.util.TokenProvider;
import com.als.SMore.user.mystudy.dto.request.IsCheckedStatusRequest;
import com.als.SMore.user.mystudy.dto.response.EnterStudy;
import com.als.SMore.user.mystudy.dto.response.EnterStudyResponse;
import com.als.SMore.user.mystudy.dto.response.StudyListResponse;
import com.als.SMore.user.mystudy.dto.response.StudyResponse;
import com.als.SMore.user.login.util.MemberUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyStudyService {
    private final StudyMemberRepository studyMemberRepository;
    private final StudyEnterMemberRepository studyEnterMemberRepository;
    private final StudyDetailRepository studyDetailRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final NotificationService notificationService;

    // 참석하는 스터디의 목록을 리턴하는 함수
    public StudyListResponse enterStudy(){
        List<StudyResponse> studyList = new ArrayList<StudyResponse>();
        Long userPk = MemberUtil.getUserPk();
        // 참가중인 study의 목록을 받아야 한다.
        List<StudyMember> admin = studyMemberRepository.findByMember_MemberPkAndRole(userPk, "member");

        if(admin.isEmpty()){
            return StudyListResponse.builder().studyList(studyList).build();
        }

        for (StudyMember studyMember : admin) {
            Long studyMemberCount = studyMemberRepository.countAllByStudy_StudyPk(studyMember.getStudy().getStudyPk());
            StudyDetail studyDetail = studyDetailRepository.findByStudy(studyMember.getStudy());
            Study study = studyRepository.findById(studyMember.getStudy().getStudyPk())
                    .orElseThrow(() -> new IllegalAccessError("존재하지 않는 스터디입니다."));

            studyList.add(
                    StudyResponse.builder()
                            .studyPk(study.getStudyPk())
                            .studyName(study.getStudyName())
                            .studyImg(studyDetail.getImageUri())
                            .studyStartDate(studyDetail.getStartDate())
                            .studyEndDate(studyDetail.getCloseDate())
                            .studyPersonNum(studyMemberCount)
                            .build()
            );
        }

        // admin 에서 나온 studyPk를 통해 스터디의 정보를 조회해야 한다.
        return StudyListResponse.builder().studyList(studyList).build();
    }

    public StudyListResponse operatedStudy(){
        StudyListResponse studyListResponse = StudyListResponse.builder().studyList(new ArrayList<>()).build();
        List<StudyResponse> studyList = new ArrayList<StudyResponse>();
        Long userPk = MemberUtil.getUserPk();
        // 운영중인 study의 목록을 받아야 한다.
        List<StudyMember> admin = studyMemberRepository.findByMember_MemberPkAndRole(userPk, "admin");

        if(admin.isEmpty()){
            return studyListResponse;
        }

        for (StudyMember studyMember : admin) {
            Long studyMemberCount = studyMemberRepository.countAllByStudy_StudyPk(studyMember.getStudy().getStudyPk());
            StudyDetail studyDetail = studyDetailRepository.findByStudy(studyMember.getStudy());
            Study study = studyRepository.findById(studyMember.getStudy().getStudyPk()).orElseThrow(IllegalAccessError::new);
            StudyResponse studyResponse = StudyResponse.builder().studyPk(study.getStudyPk()).studyName(study.getStudyName())
                    .studyImg(studyDetail.getImageUri()).studyStartDate(studyDetail.getStartDate()).studyEndDate(studyDetail.getCloseDate())
                    .studyPersonNum(studyMemberCount).build();
            studyList.add(studyResponse);
        }

        // admin 에서 나온 studyPk를 통해 스터디의 정보를 조회해야 한다.
        return studyListResponse.toBuilder().studyList(studyList).build();
    }

    @Transactional
    public EnterStudyResponse applyMemberByStudy(Long studyPk) {
        List<StudyEnterMember> studyEnterMembers = studyEnterMemberRepository.findAllByStudy_StudyPk(studyPk);
        List<EnterStudy> enterStudies = new ArrayList<>();

        if(studyEnterMembers.isEmpty()){
            return EnterStudyResponse.builder().enterStudyList(enterStudies).build();
        }

        for (StudyEnterMember studyEnterMember : studyEnterMembers) {
            Member member = memberRepository.findById(studyEnterMember.getMember().getMemberPk()).orElseThrow(IllegalAccessError::new);
            enterStudies.add(EnterStudy.builder()
                    .userId(member.getUserId())
                    .nickname(member.getNickName())
                    .profileURL(member.getProfileImg())
                    .content(studyEnterMember.getContent())
                    .build());
        }
        //스터디 방장에게 "스터디 가입 신청 요청이 있습니다." 알림
        Member studyManager = studyRepository.findMemberByStudyPk(studyPk);
        notify(studyManager.getMemberPk(), studyPk,"스터디 가입 신청 요청이 있습니다.");
        return EnterStudyResponse.builder().enterStudyList(enterStudies).build();

    }

    @Transactional
    public boolean acceptMember(IsCheckedStatusRequest statusRequest){

        StudyDetail studyDetail = studyDetailRepository.findByStudy_StudyPk(statusRequest.getStudyPk()).orElseThrow(IllegalAccessError::new);

        Long userCount = studyMemberRepository.countAllByStudy_StudyPk(statusRequest.getStudyPk());

        if(studyDetail.getMaxPeople() == userCount){
            return false;
        }

        StudyEnterMember studyEnterMember = studyEnterMemberRepository.findStudyEnterMemberByMember_UserIdAndStudy_StudyPk(
                statusRequest.getUserId(), statusRequest.getStudyPk()
        ).orElseThrow(IllegalAccessError::new);

        // 유저 이메일과 studyPk가 동일한 컬럼을 삭제하고 반환
        studyEnterMemberRepository
                .deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk(
                        statusRequest.getUserId(), statusRequest.getStudyPk()
                );

        StudyMember creatStudyMember = StudyMember.builder()
                .member(studyEnterMember.getMember())
                .study(studyEnterMember.getStudy())
                .enterDate(LocalDate.now())
                .role("member")
                .build();
        //스터디 신청한 사람한테 "스터디 가입 신청이 승낙되었습니다." 알림
        notify(studyEnterMember.getMember().getMemberPk(), studyEnterMember.getStudy().getStudyPk(), "스터디 가입 신청이 승낙되었습니다.");
        studyMemberRepository.save(creatStudyMember);
        return true;
    }

    @Transactional
    public void refuseMember(IsCheckedStatusRequest statusRequest){
        StudyEnterMember studyEnterMember = studyEnterMemberRepository.findStudyEnterMemberByMember_UserIdAndStudy_StudyPk(statusRequest.getUserId(), statusRequest.getStudyPk())
                .orElseThrow(IllegalAccessError::new);

        studyEnterMemberRepository.delete(studyEnterMember);
        //스터디 신청이 거절된 유저에게 "스터디 가입 신청이 거절되었습니다." 알림.
        notify(studyEnterMember.getMember().getMemberPk(), studyEnterMember.getStudy().getStudyPk(), "스터디 가입 신청이 거절되었습니다.");
    }

    /**
     * 스터디 탈퇴하기
     * @param studyPk 스터디Pk
     */
    @Transactional
    public void resignMemberByStudy(Long studyPk) {
        Long userPk = MemberUtil.getUserPk();
        studyMemberRepository.deleteStudyMemberByStudy_StudyPkAndMember_MemberPk(studyPk,userPk);
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
