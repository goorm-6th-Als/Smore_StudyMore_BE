package com.als.SMore.global;

import com.als.SMore.user.mypage.dto.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
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

    // 없는 URI 에 접근 할시 발생하는 예외를 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<MessageResponse> handlerNoHandlerFoundException(NoHandlerFoundException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("잘못된 URI 입니다.").build());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MessageResponse> handleMethodArgumentNotValidException(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("오류가 발생하였습니다").build());
    }

    // HTTP 메소드가 잘못 설정되어 요청하는 경우, 예외를 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<MessageResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("메소드 함수가 잘못 설정되었습니다").build());
    }

    // HTTP의 PathVariable이 누락된 경우
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<MessageResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("파라미터가 누락되었습니다").build());
    }

    // PathVariable의 이름과 URI에서 받아오는 변수의 이름이 일치하지 않을 때 발생하는 오류
    @ExceptionHandler(MissingPathVariableException.class)
    protected ResponseEntity<MessageResponse> handleMissingPathVariableException(MissingPathVariableException e){
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(MessageResponse.builder().message("파라미터의 이름과 변수의 이름이 일치하지 않습니다").build());
    }

    // RequestBody가 들어오지 않았을 때 발생하는 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<MessageResponse> handleMissingPathVariableException(HttpMessageNotReadableException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("데이터가 전달이 되지 않았습니다").build());
    }

    // pathVariable 에 원하는 자료형이 들어오지 않을 발생하는 오류 ex> Long(기대) -> String(들어온 값)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<MessageResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("타입이 일치하지 않습니다").build());
    }

    // 전달받은 데이터의 타입이 multipart/form-data이 아닐때 발생하는 오류
    @ExceptionHandler(MultipartException.class)
    protected ResponseEntity<MessageResponse> handleMultipartException(MultipartException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("content-type이 multipart/form-data 가 아닙니다").build());
    }

    // 이미지 파일을 받을때, json 파일도 같이 받을 경우, json 파일을 content-type을 명시해지 않을 경우 발생하는 오류
    @ExceptionHandler(HttpMediaTypeException.class)
    protected ResponseEntity<MessageResponse> handleHttpMediaTypeException(HttpMediaTypeException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(MessageResponse.builder().message("json 데이터에 content-type을 application/json 으로 추가해주세요").build());
    }
}
