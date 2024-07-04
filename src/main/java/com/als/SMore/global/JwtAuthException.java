package com.als.SMore.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtAuthException extends RuntimeException{
    CustomErrorCode customErrorCode;
    String accessToken;
}
