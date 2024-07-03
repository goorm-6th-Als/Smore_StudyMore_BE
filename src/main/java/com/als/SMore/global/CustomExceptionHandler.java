package com.als.SMore.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        return ErrorResponse.toResponse(e.getErrorCode());
    }

    // 현재 코드에서 header를 성정하는 코드가 없어서 추가함
    @ExceptionHandler(JwtAuthException.class)
    protected ResponseEntity<ErrorResponse> handlerJwtAuthException(JwtAuthException jwt){
        return ErrorResponse.toResponse(jwt);
    }
}
