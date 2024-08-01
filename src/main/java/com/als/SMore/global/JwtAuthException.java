package com.als.SMore.global;

import com.als.SMore.global.exception.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtAuthException extends RuntimeException{
    CustomErrorCode customErrorCode;
    String accessToken;
}
