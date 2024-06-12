package com.als.SMore.user.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class UserInfo implements OAuth2User{

    private final UserInfoDetails userInfoDetails;
    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<String,Object>() ;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        return auth;
    }

    @Override
    public String getName() {
        return userInfoDetails.getFullName();
    }


    public String getNickname(){
        return userInfoDetails.getNickName();
    }

    public Long getUserPk(){
        return userInfoDetails.getUserPk();
    }

    public String getProfileUrl(){
        return  userInfoDetails.getProfileImg();
    }

}
