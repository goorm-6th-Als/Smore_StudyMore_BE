package com.als.SMore.user.login.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class TokenProviderTest {

    @Autowired
    TokenProvider tokenProvider;

    @Test
    @DisplayName("토큰에 해당하는 역할이 없을 경우, 테스트")
    void IsCheckedRoleByStudyPk(){
        // 토큰을 생성하여, 토큰안에 없는 역할을 만들것이다.
        String accessToken = tokenProvider.generateAccessToken(1L, new HashMap<>());

        boolean checkedRole = tokenProvider.isCheckedRole(accessToken, "12");
        Assertions.assertThat(checkedRole).isEqualTo(false);
    }

    @Test
    @DisplayName("토큰에 해당하는 역할이 없을 경우, 테스트")
    void findRoleByStudyPk(){
        // 토큰을 생성하여, 토큰안에 없는 역할을 만들것이다.
        Map<String,String> role = new HashMap<>(){
            {
                put("12","user");
            }
        };

        String accessToken = tokenProvider.generateAccessToken(1L, role);

        String checkedRole = tokenProvider.getRole(accessToken, "12");
        Assertions.assertThat(checkedRole).isEqualTo("user");
    }

    @Test
    @DisplayName("토큰에 새로운 정보를 추가하는 것이 가능하지 테스트")
    void creatRenewToken() {
        String accessToken = tokenProvider.generateAccessToken(1L, new HashMap<>());

        String renewToken = tokenProvider.createRenewToken(accessToken, 12L, "admin");

        String checkedRole = tokenProvider.getRole(renewToken, "12");
        Assertions.assertThat(checkedRole).isEqualTo("admin");

    }

    @Test
    @DisplayName("토큰의 기존에 있는 정보에 새로운 정보를 추가하는 것이 가능하지 테스트")
    void creatRenewToken2() {
        Map<String,String> role = new HashMap<>(){
            {
                put("12","user");
            }
        };
        String accessToken = tokenProvider.generateAccessToken(1L, role);

        String renewToken = tokenProvider.createRenewToken(accessToken, 5L, "admin");

        String checkedRole = tokenProvider.getRole(renewToken, "5");
        Assertions.assertThat(checkedRole).isEqualTo("admin");

    }

    @Test
    @DisplayName("액세스 토큰 생성하기")
    void creatAccessToken() {
        /*Map<String,String> role = new HashMap<>(){
            {
                put("595546956868031828","user");
            }
        };*/
        Map<String,String> role = new HashMap<>(){
        };

        String accessToken = tokenProvider.generateAccessToken(8L, role);

        System.out.println(accessToken);

    }
}