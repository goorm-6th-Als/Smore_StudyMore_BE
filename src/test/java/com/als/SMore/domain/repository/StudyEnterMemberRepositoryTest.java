package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyEnterMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudyEnterMemberRepositoryTest {

    @Autowired
    private StudyEnterMemberRepository studyEnterMemberRepository;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired MemberRepository memberRepository;

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
                .entrerStatus("대기").build();

        StudyEnterMember st = studyEnterMemberRepository.save(studyEnterMember);

        studyEnterMemberRepository.deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk("tndus",study.getStudyPk());
        List<StudyEnterMember> all = studyEnterMemberRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }

}