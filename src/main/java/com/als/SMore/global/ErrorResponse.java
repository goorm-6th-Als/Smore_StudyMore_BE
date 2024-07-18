package com.als.SMore.global;

import com.als.SMore.global.exception.CustomErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String message;

    public static ResponseEntity<ErrorResponse> toResponse(CustomErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponse(JwtAuthException jwt){
        HttpHeaders httpHeaders = new HttpHeaders();
        CustomErrorCode customErrorCode = jwt.customErrorCode;
        httpHeaders.set(HttpHeaders.AUTHORIZATION,jwt.getAccessToken());
        return ResponseEntity
                .status(customErrorCode.getHttpStatus())
                .headers(httpHeaders)
                .body(ErrorResponse.builder()
                        .message(customErrorCode.getMessage())
                        .build());
    }
}
