package com.als.SMore.user.mypage.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.MemberToken;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.MemberTokenRepository;
import com.als.SMore.user.mypage.dto.request.NicknameRequest;
import com.als.SMore.user.mypage.dto.response.MessageResponse;
import com.als.SMore.user.mypage.dto.response.NicknameResponse;
import com.als.SMore.user.login.util.MemberUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;
    private final RestTemplate restTemplate;

    public NicknameResponse modifyNickname(NicknameRequest nickname){
        Long userPk = MemberUtil.getUserPk();
        Member member = memberRepository.findById(userPk).get();
        Member updatedMember = member.toBuilder().nickName(nickname.getNickname()).build();
        memberRepository.save(updatedMember);

        return NicknameResponse.builder().nickname(updatedMember.getNickName()).build();
    }

    @Transactional
    public MessageResponse deleteMember() {
        Long userPk = MemberUtil.getUserPk();
        MemberToken memberToken = memberTokenRepository.findMemberTokenByMember_MemberPk(userPk).orElseThrow(IllegalAccessError::new);
        try{
            URI uri = URI.create("https://kapi.kakao.com/v1/user/unlink");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization","Bearer "+memberToken.getKakaoAccessToken());
            HttpEntity<String> parameters = new HttpEntity<>("parameters", httpHeaders);

            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, parameters, Object.class);
            if (response.getBody() != null) {
                log.info("카카오 연동 해제에 성공했습니다.");
                log.info("응답 값 : {}",response.getBody());
            } else {
                log.info("카카오 연동 해제에 성공했습니다.");
                log.info("응답 값 : {}",response.getBody());
            }
        } catch (Exception e) {
            log.info("카카오 연동 해제 중 오류가 발생했습니다: " + e.getMessage());
        }

        memberRepository.deleteById(userPk);


        return MessageResponse.builder().message("성공적으로 탈퇴되었습니다").build();
    }
}
