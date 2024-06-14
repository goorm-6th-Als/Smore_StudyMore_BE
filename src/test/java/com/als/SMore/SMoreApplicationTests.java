package com.als.SMore;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthResponseDTO;
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
import java.util.List;

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
		LocalDateTime localDateTime = attendanceService.attendanceStart(getUser(), getStudy());
		Thread.sleep(2000);
		Long a = attendanceService.attendanceEnd(getUser(), getStudy());
		System.out.println(a);
	}

	@Test
	void start(){
		attendanceService.attendanceStart(getUser(), getStudy());
	}
	@Test
	void end(){
		Long a = attendanceService.attendanceEnd(getUser(), getStudy());
		System.out.println(a);
	}
	@Test
	void doubleStart(){
		attendanceService.attendanceStart(getUser(), getStudy());
		attendanceService.attendanceStart(getUser(), getStudy());
	}
	@Test
	void doubleEnd(){
		attendanceService.attendanceEnd(getUser(), getStudy());
		attendanceService.attendanceEnd(getUser(), getStudy());
	}


	@Test
	void addStudy(){

		StudyCreateDTO dto = StudyCreateDTO.builder()
				.memberPk(getUser())
				.studyName("진수의 스터디")
				.maxPeople(6)
				.content("이런 스터딥니다~")
				.startDate(LocalDate.now())
				.closeDate(LocalDate.now().plusYears(1))
				.build();
		studyService.createStudy(dto);

	}
	static Long getUser(){
		//return  588964788038193070L;
		return  588968050131947446L;
	}
	static Long getStudy(){
		return 588965104169702293L ;
	}
	@Test
	void addStudyMember(){
		studyMemberRepository.save(StudyMember.builder()
						.member(memberRepository.findById(getUser()).get())
						.study(studyRepository.findById(getStudy()).get())
						.role("user")
				.build());
	}

	@Test
	void dateTest(){
		List<LearningMonthResponseDTO> learningMonth = attendanceService.getLearningMonth(getUser(), getStudy(), new LearningMonthRequestDTO(2024, 6));
		for (LearningMonthResponseDTO learningMonthResponseDTO : learningMonth) {
			System.out.println(learningMonthResponseDTO.getDate() + " : " + learningMonthResponseDTO.getTime());
		}
	}
}
