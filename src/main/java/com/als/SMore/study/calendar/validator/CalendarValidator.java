package com.als.SMore.study.calendar.validator;

import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.CalendarRepository;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CalendarValidator {

    final private CalendarRepository calendarRepository;



    public Calendar getCalendar(Long calendarPk) {
        return calendarRepository.findById(calendarPk).orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_CALENDER));
    }



}
