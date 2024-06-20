package com.als.SMore.user.service;

import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.*;
import com.als.SMore.user.dto.mystudy.request.IsCheckedStatusRequest;
import com.als.SMore.user.dto.mystudy.response.EnterStudy;
import com.als.SMore.user.dto.mystudy.response.EnterStudyResponse;
import com.als.SMore.user.dto.mystudy.response.StudyListResponse;
import com.als.SMore.user.dto.mystudy.response.StudyResponse;
import com.als.SMore.user.util.MemberUtil;
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

    public StudyListResponse enterStudy(){
        List<StudyResponse> studyList = new ArrayList<StudyResponse>();
        Long userPk = MemberUtil.getUserPk();
        // 운영중인 study의 목록을 받아야 한다.
        List<StudyMember> admin = studyMemberRepository.findByMember_MemberPkAndRole(userPk, "user");

        if(admin.isEmpty()){
            return StudyListResponse.builder().studyList(studyList).build();
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

    public EnterStudyResponse applyMemberByStudy(Long studyPk) {
        List<StudyEnterMember> studyEnterMembers = studyEnterMemberRepository.findAllByStudy_StudyPk(studyPk);
        List<EnterStudy> enterStudies = new ArrayList<>();

        if(studyEnterMembers.isEmpty()){
            return EnterStudyResponse.builder().enterStudyList(enterStudies).build();
        }

        for (StudyEnterMember studyEnterMember : studyEnterMembers) {
            Member member = memberRepository.findById(studyPk).orElseThrow(IllegalAccessError::new);
            enterStudies.add(EnterStudy.builder().userId(member.getUserId()).nickname(member.getNickName()).profileURL(member.getProfileImg())
                            .content(studyEnterMember.getContent()).build());
        }
        return EnterStudyResponse.builder().enterStudyList(enterStudies).build();

    }

    public boolean acceptMember(IsCheckedStatusRequest statusRequest){

        StudyDetail studyDetail = studyDetailRepository.findByStudy_StudyPk(statusRequest.getStudyPk()).orElseThrow(IllegalAccessError::new);

        Long userCount = studyMemberRepository.countAllByStudy_StudyPk(statusRequest.getStudyPk());

        if(studyDetail.getMaxPeople() == (int) (userCount + 1)){
            return false;
        }

        // 유저 이메일과 studyPk가 동일한 컬럼을 삭제하고 반환
        StudyEnterMember studyEnterMember = studyEnterMemberRepository.deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk(statusRequest.getUserId(), statusRequest.getStudyPk())
                .orElseThrow(IllegalAccessError::new);

        StudyMember creatStudyMember = StudyMember.builder().member(studyEnterMember.getMember())
                .study(studyEnterMember.getStudy()).enterDate(LocalDate.now()).build();

        Study study = studyRepository.findById(studyEnterMember.getStudy().getStudyPk()).orElseThrow(IllegalAccessError::new);
        study.getStudyMembers().add(creatStudyMember);
        studyRepository.save(study);

        studyMemberRepository.save(creatStudyMember);
        return true;
    }

    public void refuseMember(IsCheckedStatusRequest statusRequest){
        StudyEnterMember studyEnterMember = studyEnterMemberRepository.findStudyEnterMemberByMember_UserIdAndStudy_StudyPk(statusRequest.getUserId(), statusRequest.getStudyPk())
                .orElseThrow(IllegalAccessError::new);

        StudyEnterMember renewStudyEnterMember = studyEnterMember.toBuilder().enterStatus(StudyEnterMemberStatus.REJECTED).build();
        studyEnterMemberRepository.save(renewStudyEnterMember);
    }

    public void resignMemberByStudy(Long studyPk) {
        Long userPk = MemberUtil.getUserPk();
        studyMemberRepository.deleteStudyMemberByStudy_StudyPkAndMember_MemberPk(studyPk,userPk);
    }
}
