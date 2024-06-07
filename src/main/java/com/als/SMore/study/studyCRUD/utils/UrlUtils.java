package com.als.SMore.study.studyCRUD.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// URL 포맷
public class UrlUtils {

    public static String toUrlFriendly(String input) {
        try {
            // 입력 문자열을 UTF-8로 URL 인코딩
            String urlFriendly = URLEncoder.encode(input, StandardCharsets.UTF_8.toString());
            // 공백을 %20으로 변경
            return urlFriendly.replaceAll("\\+", "-");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}