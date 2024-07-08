package com.als.SMore.global;

import com.als.SMore.user.mypage.dto.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
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

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<MessageResponse> handlerNoHandlerFoundException(NoHandlerFoundException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("잘못된 URI 입니다.").build());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MessageResponse> handleMethodArgumentNotValidException(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("오류가 발생하였습니다").build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<MessageResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("메소드 함수가 잘못 설정되었습니다").build());
    }
}
