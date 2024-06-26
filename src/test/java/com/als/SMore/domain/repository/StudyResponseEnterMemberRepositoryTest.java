package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudyResponseEnterMemberRepositoryTest {

    @Autowired
    private StudyEnterMemberRepository studyEnterMemberRepository;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired StudyMemberRepository studyMemberRepository;

    @BeforeEach
    public void setUp(){

    }

    @Test
    @DisplayName("스터디의 pk와 유저의 이메일에 동일한 회원 삭제")
    void deleteStudyEnterMemberByStudyPkAndUserId(){

        Member member = memberRepository.save(Member.builder().userId("tndus")
                .nickName("good").fullName("goodThing").build());
        Study study = studyRepository.save(Study.builder().member(member).studyName("놀자")
                .build());
        StudyEnterMember studyEnterMember = StudyEnterMember.builder().study(study).member(member)
                .enterStatus(StudyEnterMemberStatus.REJECTED).content("ji").createDate(LocalDateTime.now()).build();

        StudyEnterMember st = studyEnterMemberRepository.save(studyEnterMember);

        studyEnterMemberRepository.deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk("tndus",study.getStudyPk());
        List<StudyEnterMember> all = studyEnterMemberRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("저장 테스트")
    void saveStudyMember(){
        Member member = memberRepository.save(Member.builder().userId("tndus")
                .nickName("good").fullName("goodThing").build());
        Study study = studyRepository.save(Study.builder().member(member).studyName("놀자").studyMembers(new ArrayList<>())
                .build());
        Member member2 = memberRepository.save(Member.builder().userId("tndus")
                .nickName("good").fullName("goodThing").build());
        StudyMember save = studyMemberRepository.save(StudyMember.builder().member(member2).study(study).role("user").enterDate(LocalDate.now()).build());
        study.getStudyMembers().add(save);

        studyRepository.save(study);

        int size = studyRepository.findById(study.getStudyPk()).get().getStudyMembers().size();
        assertThat(size).isEqualTo(1);
    }

}