package com.als.SMore.study.chatting.DTO;


import com.als.SMore.user.util.MemberUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDTO {
    //나중에 UserDetails에서 가져오는 거로 바꾸고 content 남기고 다 제거해야함
//    private String studyPk;
//    private Long memberPk ;//
//    private String memberName;//
//    private String profileImageUrl;//
    private String content;

}
