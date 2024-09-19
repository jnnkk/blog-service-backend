package com.blog_service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 유효성 검사 실패 예외 처리, ResponseEntityExceptionHandler를 상속받아 구현
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        // 에러 메시지를 담을 Map 생성 (statusCode, timestamp, messages)
        Map<String, Object> body = new HashMap<>();

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage()) // 에러 메시지 추출
                .collect(Collectors.toList());
        body.put("messages", errors);
        body.put("timestamp", LocalDateTime.now());
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // 사용자 정의 예외 처리(테스트)
    @ExceptionHandler(CustomException.class)
    public ErrorResponse handleCustomException(CustomException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();
    }
}
