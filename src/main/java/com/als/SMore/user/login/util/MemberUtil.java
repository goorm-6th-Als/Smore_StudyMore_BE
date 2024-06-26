package com.als.SMore.user.login.util;

import com.als.SMore.user.login.dto.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public class MemberUtil {

    public static String getName() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo.getName();
    }

    public static String getNickname(){
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo.getNickname();
    }

    public static Long getUserPk(){
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo.getUserPk();
    }

    public static String getProfileUrl(){
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  userInfo.getProfileUrl();
    }
}
