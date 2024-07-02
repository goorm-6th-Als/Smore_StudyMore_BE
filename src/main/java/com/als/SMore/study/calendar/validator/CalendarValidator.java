package com.als.SMore.study.calendar.validator;

import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.CalendarRepository;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CalendarValidator {

    final private CalendarRepository calendarRepository;
    final private MemberRepository memberRepository;
    final private StudyRepository studyRepository;

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

    public Calendar getCalendar(Long calendarPk) {
        return calendarRepository.findById(calendarPk).orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_CALENDER));
    }


//    public void deleteCalender(Member member, Study study, Calender calender){
//        isManager(member, study, calender);
//        calenderRepository.delete(calender);
//    }


}
