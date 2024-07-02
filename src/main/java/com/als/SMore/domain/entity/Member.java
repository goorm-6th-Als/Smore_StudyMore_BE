package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "member")
@Getter @Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @Tsid
    @Column(name = "member_pk")
    private Long memberPk;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private MemberToken memberToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AttendanceCheck> attendanceCheck;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Calendar> calendars;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PersonalTodo> personalTodos;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Problem> problems;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudyEnterMember> studyEnterMembers;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudyMember> studyMembers;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudyProblemBank> studyProblemBanks;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudySchedule> studySchedules;
}
