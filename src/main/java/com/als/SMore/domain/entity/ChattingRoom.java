package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chatting_room")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoom {

    @Id @Tsid
    @Column(name = "chatting_room_pk")
    private Long chattingRoomPk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "room_name")
    private String roomName;
}
