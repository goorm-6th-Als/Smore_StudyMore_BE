package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "chatting")
public class Chatting {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_pk", nullable = false)
    private Long chattingPk;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chatting_room_pk", nullable = false)
    private ChattingRoom chattingRoom;

    @Lob
    @Column(name = "chat_content", nullable = false)
    private String chatContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    // Getters and Setters
}
