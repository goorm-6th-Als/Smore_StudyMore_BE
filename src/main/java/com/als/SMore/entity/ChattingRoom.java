package com.als.SMore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chatting_room")
public class ChattingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_room_pk")
    private Long chattingRoomPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "room_name")
    private String roomName;

    // Getters and Setters
}
