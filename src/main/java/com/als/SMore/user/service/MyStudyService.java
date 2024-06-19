package com.als.SMore.user.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyEnterMember;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyEnterMemberRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.user.dto.mystudy.request.IsCheckedStatusRequest;
import com.als.SMore.user.util.MemberUtil;
import jakarta.annotation.PostConstruct;
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
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    public List<String> enterStudy(){
        Long userPk = MemberUtil.getUserPk();
        // 운영중인 study의 목록을 받아야 한다.
        List<StudyMember> admin = studyMemberRepository.findByMember_MemberPkAndRole(userPk, "admin");

        if(admin.isEmpty()){
            return new ArrayList<>();
        }

        // admin 에서 나온 studyPk를 통해 스터디의 정보를 조회해야 한다.
        return null;
    }

    public boolean acceptMember(IsCheckedStatusRequest statusRequest){
        // 유저 이메일과 studyPk가 동일한 컬럼을 삭제하고 반환
        StudyEnterMember studyEnterMember = studyEnterMemberRepository.deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk(statusRequest.getUserId(), statusRequest.getStudyPk())
                .orElseThrow(IllegalAccessError::new);

        StudyMember creatStudyMember = StudyMember.builder().member(studyEnterMember.getMember())
                .study(studyEnterMember.getStudy()).enterDate(LocalDate.now()).build();

        studyMemberRepository.save(creatStudyMember);
        return true;
    }
}
