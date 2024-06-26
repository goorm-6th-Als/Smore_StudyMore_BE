package com.als.SMore.user.login.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Set;

public class KakaoUserInfo {

    public static final String KAKAO_ACCOUNT = "kakao_account";
    public static final String EMAIL = "email";

    public static final String NAME = "name";
    public static final String NICKNAME = "nickname";
    public static final String PROFILE = "profile";
    public static final String PROFILE_IMG_URL = "thumbnail_image_url";
    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public String getEmail() {
        Map<String, Object> account = getKakaoAccount();
        return (String) account.get(EMAIL);
    }

    public Map<String, Object> getKakaoAccount() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};

        Object kakaoAccout = attributes.get(KAKAO_ACCOUNT);
        Map<String, Object> account = objectMapper.convertValue(kakaoAccout, typeReference);
        return account;
    }

    public String getName(){
        Map<String, Object> account = getKakaoAccount();
        return (String) account.get(NAME);
    }

    public Map<String, Object> getProfileVales() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};
        Map<String, Object> account = getKakaoAccount();
        Object profile = account.get(PROFILE);

        Map<String, Object> profileValues = objectMapper.convertValue(profile, typeReference);
        return profileValues;
    }

    public String getNickname(){
        Map<String, Object> profileVales = getProfileVales();
        return (String) profileVales.get(NICKNAME);
    }

    public String getProfileImgUrl(){
        Map<String, Object> profileVales = getProfileVales();
        return (String) profileVales.get(PROFILE_IMG_URL);
    }
}
