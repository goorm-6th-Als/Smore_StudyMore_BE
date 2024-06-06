package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "chatting")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chatting {

    @Id @Tsid
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
}
