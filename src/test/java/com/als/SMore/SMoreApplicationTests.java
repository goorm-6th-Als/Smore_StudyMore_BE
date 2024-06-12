package com.als.SMore;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.attendance.service.AttendanceService;
import com.als.SMore.study.studyCRUD.DTO.StudyCreateDTO;
import com.als.SMore.study.studyCRUD.DTO.StudyDTO;
import com.als.SMore.study.studyCRUD.service.StudyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest

class SMoreApplicationTests {
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	StudyRepository studyRepository;
	@Autowired
	StudyDetailRepository studyDetailRepository;
	@Autowired
	StudyMemberRepository studyMemberRepository;

	@Autowired
	StudyService studyService;
	@Test
	void normal() throws InterruptedException {
		LocalDateTime localDateTime = attendanceService.attendanceStart(588263439527058109L, 588268389100850017L);
		Thread.sleep(2000);
		Long a = attendanceService.attendanceEnd(588263439527058109L, 588268389100850017L);
		System.out.println(a);
	}

	@Test
	void start(){
		attendanceService.attendanceStart(588263439527058109L, 588268389100850017L);
	}
	@Test
	void end(){
		Long a = attendanceService.attendanceEnd(588263439527058109L, 588268389100850017L);
		System.out.println(a);
	}
	@Test
	void doubleStart(){
		attendanceService.attendanceStart(588263439527058109L, 588268389100850017L);
		attendanceService.attendanceStart(588263439527058109L, 588268389100850017L);
	}
	@Test
	void doubleEnd(){
		attendanceService.attendanceEnd(588263439527058109L, 588268389100850017L);
		attendanceService.attendanceEnd(588263439527058109L, 588268389100850017L);
	}
	@Test
	void addMember(){
		memberRepository.save(new Member().toBuilder()
						.userPassword("비밀비밀~")
						.userId("일빠인가?")
						.nickName("진수")
						.fullName("박진수")
				.build());
	}

	@Test
	void addStudy(){

		StudyCreateDTO dto = StudyCreateDTO.builder()
				.memberPk(588263439527058109L)
				.studyName("진수의 스터디")
				.maxPeople(6)
				.content("이런 스터딥니다~")
				.startDate(LocalDate.now())
				.closeDate(LocalDate.now().plusYears(1))
				.build();
		studyService.createStudy(dto);

	}

}