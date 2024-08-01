package com.als.SMore.global.validator;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.log.timeTrace.TimeTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@TimeTrace
public class GlobalValidator {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    public Member getMember(Long pk) {
        // 멤버 PK를 받아서 검증
        Optional<Member> member = memberRepository.findById(pk);
        return member.orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER));
    }

    public Study getStudy(Long pk) {
        // Study PK를 받아서 검증
        Optional<Study> study = studyRepository.findById(pk);
        return study.orElseThrow(()-> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));

    }


}
