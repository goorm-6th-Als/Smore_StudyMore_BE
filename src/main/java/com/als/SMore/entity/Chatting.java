package com.als.SMore.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chatting")
public class Chatting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_pk")
    private Long chattingPk;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chatting_room_pk", nullable = false)
    private ChattingRoom chattingRoom;

    @Column(name = "chat_content", nullable = false)
    private String chatContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    // Getters and Setters
}

